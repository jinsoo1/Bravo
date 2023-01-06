package com.bravo.android.bravo.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import gun0912.tedbottompicker.util.RealPathUtil
import java.io.*

private const val IMAGE_JPEG_SUFFIX = ".jpg"
private const val IMAGE_MIME_TYPE = "image/jpeg"
private const val IMAGE_MIME_TYPES = "image/webp"

internal class MediaUtil {

    companion object {
        internal fun Bitmap.saveToGallery(context: Context): Uri? {
            val imageOutputStream: OutputStream

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = context.contentResolver
                    val contentValues = ContentValues()

                    contentValues.apply {
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/seecle/") // 경로 설정
                        put(
                            MediaStore.MediaColumns.DISPLAY_NAME,
                            "${System.currentTimeMillis()}"
                        )
                        put(MediaStore.MediaColumns.MIME_TYPE, IMAGE_MIME_TYPE)
                    }
                    val imageUri =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    imageOutputStream = resolver.openOutputStream(imageUri!!)!!
                    imageOutputStream.use {
                        this.compress(Bitmap.CompressFormat.JPEG, 90, it)
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                            this.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 90, it)
//                        } else {
//                            this.compress(Bitmap.CompressFormat.WEBP, 90, it)
//                        }
                    }
                    //Toast.makeText(context, "Image saved to gallery1", Toast.LENGTH_SHORT).show()

                    return imageUri
                } else {
                    val imageUrl = MediaStore.Images.Media.insertImage(
                        context.contentResolver,
                        this,
                        "${System.currentTimeMillis()}",
                        "${context.applicationInfo.loadLabel(context.packageManager)}-image"
                    )
                    val savedImageUri = Uri.parse(imageUrl)

                    //Toast.makeText(context, "Image saved to gallery2", Toast.LENGTH_SHORT).show()

                    return savedImageUri
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Image not saved \n$e", Toast.LENGTH_SHORT).show()
            }
            return null
        }

        internal fun Context.getMediaUri(): Uri {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}$IMAGE_JPEG_SUFFIX")
                put(MediaStore.MediaColumns.MIME_TYPE, IMAGE_MIME_TYPE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
            }
            return contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )!!
        }

        internal fun Context.scanMediaToBitmap(uri: Uri, action: (Bitmap) -> Unit) {
            MediaScannerConnection.scanFile(this, arrayOf(uri.path), null) { _, _ ->
                val bmp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(contentResolver, uri)
                    )
                } else {
                    val originalBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    originalBitmap.rotateFromGalleryPreVersionP(this, uri)
                }
                action.invoke(bmp)
            }
        }

        private fun Bitmap.rotateFromGalleryPreVersionP(context: Context, uri: Uri): Bitmap {
            val path = context.getFilePath(uri)
            return Bitmap.createBitmap(
                this,
                0,
                0,
                width,
                height,
                Matrix().apply {
                    postRotate(
                        calculateExif(path)
                    )
                },
                false
            )
        }

        @SuppressLint("Range")
        private fun Context.getFilePath(uri: Uri): String {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.moveToNext()
            val path = cursor?.getString(cursor.getColumnIndex("_data"))
            cursor?.close()

            if (path == null) {
                Toast.makeText(this, "not found file path!", Toast.LENGTH_SHORT).show()
                throw NullPointerException()
            } else {
                return path
            }
        }

        /**
         * Mac의 경우 Orientation 해석이 다를 수 있어서 테스트 시 실기기를 사용해야함
         * https://stackoverflow.com/questions/39400351/android-exif-data-always-0-how-to-change-it/39567169
         */
        private fun calculateExif(path: String): Float {
            val attribute = ExifInterface(path).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            return when (attribute) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
        }



        fun resizeBitmapImage(source: Uri, maxResolutionX: Int, maxResolutionY: Int, context: Activity): Uri? {

            var bitmap: Bitmap? = null

            try {
                bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, source))
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, source)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("BITMAPSSEx", e.toString())
            }

            Log.d("BITMAPSS", bitmap.toString())

            val width: Int = bitmap!!.width
            val height: Int = bitmap!!.height

            //이거보다 작으면 그냥 업로드
            if (width < 1280 && height < 1280){
                var uri : Uri? = null
                uri = bitmap.saveToGallery(context)

                return uri
            }

            var x = 0
            var y = 0

            if (width > 3000) {
                x = (width - 3000) / 2
            }

            if (height > 3000) {
                y = (height - 3000) / 2
            }
            var cw: Int = 3000 // crop width

            var ch: Int = 3000 // crop height

            if (3000 > width) cw = width

            if (3000 > height) ch = height


            var uri : Uri? = null
            bitmap = Bitmap.createBitmap(bitmap, x, y, cw, ch)

            val widths = bitmap!!.width
            val heights = bitmap!!.height
            var newWidth = widths
            var newHeight = heights
            var rate = 0.0f
            if (widths >= heights) {  //가로라면
                if (maxResolutionY < widths) {
                    rate = maxResolutionY / widths.toFloat()
                    newHeight = (heights * rate).toInt()
                    newWidth = maxResolutionX
                }
            } else {  //세로라면
                if (maxResolutionY < heights) {
                    rate = maxResolutionY / heights.toFloat()
                    newWidth = (widths * rate).toInt()
                    newHeight = maxResolutionY
                }
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

            uri = bitmap.saveToGallery(context)

            return uri
        }

        fun saveImageOnAboveAndroidQ(context: Context,bitmap: Bitmap) : Uri {
            val fileName = System.currentTimeMillis().toString() + ".jpg" // 파일이름 현재시간.png

            /*
            * ContentValues() 객체 생성.
            * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
            * */
            val contentValues = ContentValues()
            contentValues.apply {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/seecle") // 경로 설정
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
                // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
            }

            // 이미지를 저장할 uri를 미리 설정해놓는다.
            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            try {
                if(uri != null) {
                    val image = context.contentResolver.openFileDescriptor(uri, "w", null)
                    // write 모드로 file을 open한다.

                    if(image != null) {
                        val fos = FileOutputStream(image.fileDescriptor)
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                        //비트맵을 FileOutputStream를 통해 compress한다.
                        fos.close()
                        bitmap.recycle()

                        contentValues.clear()
                        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                        context.contentResolver.update(uri, contentValues, null, null)
                    }
                }
            } catch(e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return uri!!
        }


        private fun saveImageOnUnderAndroidQ(bitmap: Bitmap) : Uri? {
            val fileName = System.currentTimeMillis().toString() + ".jpg"
            val externalStorage = Environment.getExternalStorageDirectory().absolutePath
            val path = "$externalStorage/Pictures/seecle"
            val dir = File(path)

            if(dir.exists().not()) {
                dir.mkdirs() // 폴더 없을경우 폴더 생성
            }
            val fileItem = File("$dir/$fileName")
            try {

                fileItem.createNewFile()
                //0KB 파일 생성.

                val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

                fos.close() // 파일 아웃풋 스트림 객체 close

                //sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
                // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return Uri.fromFile(fileItem)

        }



        fun getPath(context: Context?, uri: Uri): String? {
            // DocumentProvider
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(
                    context,
                    uri
                )
            ) {
                // ExternalStorageProvider
                if (RealPathUtil.isExternalStorageDocument(uri)) {
                    Log.d("asdasdqwe1", uri.toString())
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getDataDirectory().toString() + "/" + split[1]
                    } else {
                        Toast.makeText(
                            context,
                            "Could not get file path. Please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (RealPathUtil.isDownloadsDocument(uri)) {
                    Log.d("asdasdqwe2", uri.toString())
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                    )
                    return RealPathUtil.getDataColumn(context, contentUri, null, null)
                } else if (RealPathUtil.isMediaDocument(uri)) {
                    Log.d("asdasdqwe3", uri.toString())
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    contentUri = if ("image" == type) {
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    } else {
                        MediaStore.Files.getContentUri("external")
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(
                        split[1]
                    )
                    return RealPathUtil.getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                Log.d("asdasdqwe4-1", uri.toString())
                Log.d("asdasdqwe4", RealPathUtil.getDataColumn(context, uri, null, null))
                return RealPathUtil.getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                Log.d("asdasdqwe5", uri.path!!)
                return uri.path
            }
            return null
        }




    }

}