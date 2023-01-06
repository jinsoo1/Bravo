package com.bravo.android.bravo.ui.view.main.community.together

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.remote.source.TogetherDataSource
import com.bravo.android.bravo.util.Event
import com.bravo.android.bravo.util.ext.toRequestBody
import io.reactivex.rxkotlin.addTo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TogetherViewModel(
    private val togetherDataSource: TogetherDataSource
) : BaseViewModel() {

    private val _action : MutableLiveData<Event<TogetherAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherAction>> get() = _action

    private val _category : MutableLiveData<String> = MutableLiveData()
    val category : LiveData<String> get() = _category

    private val _title : MutableLiveData<String> = MutableLiveData()
    val title : LiveData<String> get() = _title

    private val _content : MutableLiveData<String> = MutableLiveData()
    val content : LiveData<String> get() = _content

    var uploadImg : ArrayList<String> = ArrayList()

    var photo : ArrayList<String> = ArrayList()

    private val _peopleNum : MutableLiveData<Int> = MutableLiveData()
    val peopleNum : MutableLiveData<Int> get() = _peopleNum

    //false = 오프라인, true = 온라인
    private val _location : MutableLiveData<Int> = MutableLiveData()
    val location : LiveData<Int> get() = _location

    private val _locationDetail : MutableLiveData<Int?> = MutableLiveData()
    val locationDetail : LiveData<Int?> get() = _locationDetail

    private val _gender : MutableLiveData<String> = MutableLiveData()
    val gender : LiveData<String> get() = _gender

    //false = 무관, true = 직접입력
    private val _ageState : MutableLiveData<Int> = MutableLiveData()
    val ageState : LiveData<Int> get() = _ageState

    //직접입력시 나이설정
    private val _firstAge : MutableLiveData<String?> = MutableLiveData()
    val firstAge : LiveData<String?> get() = _firstAge

    //직접입력시 나이설정
    private val _secondAge : MutableLiveData<String?> = MutableLiveData()
    val secondAge : LiveData<String?> get() = _secondAge

    private val _date : MutableLiveData<String> = MutableLiveData()
    val date : LiveData<String> get() = _date

    //false = 일회성, true = 정기활동
    private val _activityState : MutableLiveData<Int> = MutableLiveData()
    val activityState : LiveData<Int> get() = _activityState


    //false = 매일, true = 요일선택
    private val _dayState : MutableLiveData<Boolean> = MutableLiveData()
    val dayState : LiveData<Boolean>get() = _dayState


    private val _dayListState : MutableLiveData<String> = MutableLiveData()
    val dayListState : LiveData<String> get() = _dayListState

    fun setLogTest(){
        Log.d("asdf123sd", "활동주제 : " + _category.value + "제목 : " + title.value + "활동 내용 : " +
                content.value + "이미지 : " + photo + "인원수 : " + peopleNum.value + "지역 : " +
                location.value + "성별 : " + gender.value + "나이 : " + ageState.value + "첫번째나이 : " +
                firstAge.value + "두번째나이 : " + secondAge.value + "활동일정 : " + activityState.value + "날짜" +
                date.value + "요일 : " + dayState.value + "요일리스트 : " + dayListState.value)
    }


    fun settingCategory(category : String){
        _category.value = category
    }

    fun settingTitleContent(title : String, content : String, uri : ArrayList<String>){
        _title.value = title
        _content.value = content
        photo.addAll(uri)
    }

    fun settingPeopleNumLocation(peopleNum : Int, location : Boolean){
        _peopleNum.value = peopleNum
        if(location){
            _location.value = 1
        }else{
            _location.value = 0
        }

    }

    fun settingGenderAge(gender : String, ageState : Boolean, firstAge : String?, secondAge : String?){
        _gender.value = gender
        if(ageState){
            _ageState.value = 1
        }else{
            _ageState.value = 0
        }
        _firstAge.value = firstAge
        _secondAge.value = secondAge
    }

    fun settingActivityFalse(month : String, day : String, time : String){
        val current = LocalDateTime.now()
        val formatterY = DateTimeFormatter.ofPattern("yyyy")
        _date.value = current.format(formatterY) + month + day + time
        _activityState.value = 0
    }

    fun settingActivityTrue(state : Boolean, dayList : String){
        _dayState.value = state
        _date.value = dayList

        _activityState.value = 1
    }

    lateinit var file: File
    fun saveTogetherData(){
        togetherDataSource.saveTogetherData(
            _title.value!!.toRequestBody(),
            _content.value!!.toRequestBody(),
            uploadImg.map { it.toRequestBody() },
            _peopleNum.value.toString().toRequestBody(),
            _location.value.toString().toRequestBody(),
            _locationDetail.value.toString().toRequestBody(),
            _gender.value!!.toRequestBody(),
            _ageState.value.toString().toRequestBody(),
            _firstAge.value.toString().toRequestBody(),
            _secondAge.value.toString().toRequestBody(),
            _activityState.value.toString().toRequestBody(),
            _date.value!!.toRequestBody(),
            _category.value.toString().toRequestBody(),
            photo.map {
                file = File(it)
                MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        )
            .subscribe({
                _action.value = Event(TogetherAction.FINISH)
            },{
                Log.d("saveTogetherData E", it.toString())
            })
            .addTo(compositeDisposable)
    }

enum class TogetherAction{
    FINISH
}

}