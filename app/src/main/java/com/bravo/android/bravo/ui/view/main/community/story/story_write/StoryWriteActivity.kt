package com.bravo.android.bravo.ui.view.main.community.story.story_write

import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.bravo.android.bravo.R
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseRecyclerAdapter
import com.bravo.android.bravo.base.BaseVmActivity
import com.bravo.android.bravo.data.remote.source.StoryDataSource
import com.bravo.android.bravo.databinding.ActivityStoryWriteBinding
import com.bravo.android.bravo.databinding.ItemImageBinding
import com.bravo.android.bravo.util.EventObserver
import com.bravo.android.bravo.util.MediaUtil.Companion.getPath
import com.bravo.android.bravo.util.ext.toRequestBody
import gun0912.tedbottompicker.TedRxBottomPicker
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import java.io.File

class StoryWriteActivity : BaseVmActivity<ActivityStoryWriteBinding>(
    R.layout.activity_story_write,
    StoryWriteViewModel::class.java
) {
    override val viewModel by lazy { vm as StoryWriteViewModel }
    override val toolbarId = 0

    private val storyDataSource : StoryDataSource by inject()

    private val category by lazy {
        intent.getStringExtra("category")
    }


    override fun initActivity() {

        viewModel.setObserves()

        binding.apply {
            editor.apply {
                setEditorHeight(200)
                setEditorFontSize(22)
                setEditorFontColor(Color.GRAY)
                setPadding(10, 10, 10, 10)
                setPlaceholder("여기에 내용을 적어주세요.")
                setBackgroundColor(Color.parseColor("#F6F6F6"))
                setOnTextChangeListener {
                    //preview.text = it
                    viewModel.settingUploadHtml(it)
                }
            }

            rvImage.apply {
                adapter = ImageListAdapter(viewModel)
            }

            ivGallery.apply{
                setOnClickListener {
                    startLocationPermissionRequest()
                    //ImgPicker.start(it)
                }
            }

        }


    }

    private fun StoryWriteViewModel.setObserves(){
        action.observe(this@StoryWriteActivity, EventObserver{
            when(it){
                StoryWriteViewModel.StoryWriteAction.UPLOAD -> storyUpload()
            }
        })

        deleteUri.observe(this@StoryWriteActivity, EventObserver{
            val delete = "<img src=\"$it\" alt=\"alt\" style=\"max-width:100%; height:auto; margin-top:10px; margin-bottom:10px;\">"
            uploadHtml.value =  uploadHtml.value?.replace(delete.toRegex(), "")
            binding.editor.html = uploadHtml.value?.replace(delete.toRegex(), "")
//            selectedUriList.removeIf { uri ->
//                uri == it.toUri()
//            }
        })

        uploadHtml.observe(this@StoryWriteActivity, Observer {
            binding.btnUpload.isEnabled = it.isNotEmpty()
        })

        listUrl.observe(this@StoryWriteActivity, Observer { it ->
            Log.d("storyUpload", it.toString())
            viewModelScope.launch{
                viewModel.url.clear()
                it.forEach { its ->
                    viewModel.url.add(its!!)
                }
            }
        })
    }

    private var multiImageDisposable: Disposable? = null
    private var selectedUriList : MutableList<Uri> = mutableListOf()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted){

            multiImageDisposable = TedRxBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("완료")
                .setEmptySelectionText("선택된 이미지 없음")
                .setSelectedUriList(selectedUriList)
                .showMultiImage()
                .subscribe({ it ->
                    selectedUriList = it;

                    val uriList : MutableList<String> = mutableListOf()
                    runBlocking {
                        launch {
                            selectedUriList.forEach { it ->
                                val delete = "<img src=\"$it\" alt=\"alt\" style=\"max-width:100%; height:auto; margin-top:10px; margin-bottom:10px;\">"
                                if(viewModel.uploadHtml.value?.indexOf(delete) == -1) {
                                    binding.editor.insertImage(
                                        it.toString(),
                                        "alt\" style=\"max-width:100%; height:auto; margin-top:10px; margin-bottom:10px;"
                                    )
                                    uriList.add(it.toString())
                                    viewModel.photo.add(getPath(this@StoryWriteActivity, it)!!)
                                }
                            }
                        }
                    }
                    viewModel.settingUriList(uriList)
                    selectedUriList = mutableListOf()

                }, Throwable::printStackTrace)

        }else{
            //권한 획득 거부시
            toast("이미지를 등록할 수 없습니다..")?.show()
        }
    }

    // Ex. Launching ACCESS_FINE_LOCATION permission.
    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


    lateinit var file: File
    fun storyUpload(){
        Log.d("storyUpload1",  viewModel.photo.toString())
        Log.d("storyUpload2",  viewModel.url.toString())
        storyDataSource.storyUpload(
            viewModel.uploadHtml.value!!.toRequestBody(),
            category!!.toRequestBody(),
            viewModel.url.map { it.toRequestBody()},
            viewModel.photo.map{
                file = File(it)
                MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        )
            .subscribe({
                Log.d("storyUpload", it.message)
                toast("글 작성이 완료되었습니다.")?.show()
                finish()
            },{
                Log.d("storyUpload E", it.toString())
            })
            .addTo(viewModel.compositeDisposable)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//            val images = ImagePicker.getImages(data)
//            insertImages(images)
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun insertImages(images: List<Image>?) {
//        if (images == null) return
//        val stringBuffer = StringBuilder()
//        var i = 0
//        val l = images.size
//        while (i < l) {
//            stringBuffer.append(images[i].path).append("\n")
//            // Handle this
//            binding.editor.insertImage("file://" + images[i].path, "alt\" style=\"max-width:100%; height:auto")
//            i++
//        }
//    }




}

class ImageListAdapter(vm : StoryWriteViewModel) : BaseRecyclerAdapter<String, ItemImageBinding>(
    R.layout.item_image, vm
){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        (holder.binding as ItemImageBinding).apply {
            ivCancel.setOnClickListener{
                vm!!.deleteImage(position)
            }
        }

    }
}
