package com.bravo.android.bravo.ui.view.main.community.together.fragment.third

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class TogetherThirdViewModel : BaseViewModel(){

    private val _action : MutableLiveData<Event<TogetherThirdAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherThirdAction>> get() = _action

    private val _peopleNum : MutableLiveData<Int> = MutableLiveData(1)
    val peopleNum : MutableLiveData<Int> get() = _peopleNum

    private val _location : MutableLiveData<Boolean> = MutableLiveData(false)
    val location : LiveData<Boolean> get() = _location




    fun setOnNextAction(){
        _action.value = Event(TogetherThirdAction.NEXT)
    }

    fun peopleNumPlus(){
        if(_peopleNum.value!! < 10){
            _peopleNum.value = _peopleNum.value!!.plus(1)
        }

    }
    fun peopleNumMinus(){
        if(_peopleNum.value!! > 1){
            _peopleNum.value = _peopleNum.value!!.minus(1)
        }
    }
    fun setLocation(state : Boolean){
        _location.value = state
    }

    enum class TogetherThirdAction{
        NEXT
    }
}