package com.bravo.android.bravo.ui.view.main.community.together.fragment.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class TogetherFirstViewModel : BaseViewModel() {


    private val _actionTogether : MutableLiveData<Event<TogetherFirstAction>> = MutableLiveData()
    val actionTogether : LiveData<Event<TogetherFirstAction>> get() = _actionTogether

    private val _category : MutableLiveData<String> = MutableLiveData()
    val category : MutableLiveData<String> get() = _category

    fun settingCategory(category : String){
        _category.value = category
    }


    fun onTogetherBtnClick(item : Int){
        when(item){
            1 -> _actionTogether.value = Event(TogetherFirstAction.EXERCISE)
            2 -> _actionTogether.value = Event(TogetherFirstAction.HOBBY)
            3 -> _actionTogether.value = Event(TogetherFirstAction.SOCIAL)
            4 -> _actionTogether.value = Event(TogetherFirstAction.FAMOUS)
            5 -> _actionTogether.value = Event(TogetherFirstAction.PET)
            6 -> _actionTogether.value = Event(TogetherFirstAction.ETC)
            7 -> _actionTogether.value = Event(TogetherFirstAction.NEXT)
        }
    }

    enum class TogetherFirstAction{
        EXERCISE, HOBBY, SOCIAL, FAMOUS, PET, ETC, NEXT
    }
}