package com.bravo.android.bravo.util.ext

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun String.toRequestBody() = RequestBody.create("plain/text".toMediaTypeOrNull(), this)

object StringExt {
    private val shortDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val longDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA)


    fun showDateAsShort(origin: String): String {
        return shortDateFormat.format(longDateFormat.parse(origin) ?: return "")
    }

    fun yearSlice(year : Int) : String{
        try {
            val ran = IntRange(2,3)
            return year.toString().slice(ran)+"년생"
        }catch (e : Exception){
            return "년생"
        }

    }

    fun regionSlice(sido: String): String {
        var resion : String
        try {
            runBlocking {
                resion = sido.replace("특별시", "")
                resion = resion.replace("광역시", "")
                resion = resion.replace("특별자치시", "")
                resion = resion.replace("북도", "")
                resion = resion.replace("남도", "")
                resion = resion.replace("특별자치도", "")
                resion = resion.replace("도", "")
            }
            return resion

        }catch (e : Exception){
            return "12"
        }



    }

    fun removeHTMLTag(changeStr: String?): String? {
        var changeStr = changeStr
        changeStr = if (changeStr != null && changeStr != "") {
            changeStr.replace("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>".toRegex(), "")
        } else {
            ""
        }
        return changeStr
    }

    fun settingTime(time : String) : String{
        var getTime = time
        if(time.toInt() < 1000){
            if(time.toInt() < 100){
                getTime = "00$time"
            }else{
                getTime = "0$time"
            }
        }
        Log.d("asdasdfasdf", getTime)
        val hour = getTime.substring(0 until 2)
        val minute = getTime.substring(2 until 4)
        val times = "$hour:$minute"

        return times
    }

    fun removeLastNchars(str: String, n: Int): String {
        return str.substring(0, str.length - n)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateTimeToMillSec(dateTime: String): Long{
        var timeInMilliseconds: Long = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val mDate = sdf.parse(dateTime)
            timeInMilliseconds = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    fun calculationTime(createDateTime: String): String{
        val nowDateTime = Calendar.getInstance().timeInMillis //현재 시간 to millisecond
        var value = ""
        val differenceValue = nowDateTime - dateTimeToMillSec(createDateTime) //현재 시간 - 비교가 될 시간
        when {
            differenceValue < 60000 -> { //59초 보다 적다면
                value = "방금 전"
            }
            differenceValue < 3600000 -> { //59분 보다 적다면
                value =  TimeUnit.MILLISECONDS.toMinutes(differenceValue).toString() + "분 전"
            }
            differenceValue < 86400000 -> { //23시간 보다 적다면
                value =  TimeUnit.MILLISECONDS.toHours(differenceValue).toString() + "시간 전"
            }
            differenceValue <  604800000 -> { //7일 보다 적다면
                value =  TimeUnit.MILLISECONDS.toDays(differenceValue).toString() + "일 전"
            }
            differenceValue < 2419200000 -> { //3주 보다 적다면
                value =  (TimeUnit.MILLISECONDS.toDays(differenceValue)/7).toString() + "주 전"
            }
            differenceValue < 31556952000 -> { //12개월 보다 적다면
                value =  (TimeUnit.MILLISECONDS.toDays(differenceValue)/30).toString() + "개월 전"
            }
            else -> { //그 외
                value =  (TimeUnit.MILLISECONDS.toDays(differenceValue)/365).toString() + "년 전"
            }
        }
        return value
    }
}
