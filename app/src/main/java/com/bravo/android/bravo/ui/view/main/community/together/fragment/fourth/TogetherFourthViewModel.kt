package com.bravo.android.bravo.ui.view.main.community.together.fragment.fourth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class TogetherFourthViewModel : BaseViewModel() {


    private val _action : MutableLiveData<Event<TogetherFourthAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherFourthAction>> get() = _action

    private val _gender : MutableLiveData<String> = MutableLiveData("무관")
    val gender : LiveData<String> get() = _gender

    private val _ageState : MutableLiveData<Boolean> = MutableLiveData(false)
    val ageState : LiveData<Boolean> get() = _ageState

    val firstAge : MutableLiveData<String?> = MutableLiveData()
    val secondAge : MutableLiveData<String?> = MutableLiveData()

    fun setGenderValue(gender : String){
        _gender.value = gender
    }

    fun setAgeState(state : Boolean){
        if(!state){
            firstAge.postValue(null)
            secondAge.postValue(null)
        }
        _ageState.value = state
    }

    fun setOnNextAction(){
        _action.value = Event(TogetherFourthAction.NEXT)
    }


    enum class TogetherFourthAction(){
        NEXT
    }
}