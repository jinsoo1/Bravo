package com.bravo.android.bravo.ui.view.mypage

import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class MyPageNavViewModel: BaseViewModel() {

    val action: MutableLiveData<Event<MyPageActions>> = MutableLiveData()


    fun clickSetting(){
        action.value = Event(MyPageActions.SETTING)
    }

    fun clickProfileEdit(){
        action.value = Event(MyPageActions.EDIT)
    }

    fun clickPosting(){
        action.value = Event(MyPageActions.POST)
    }

    fun clickInformation(){
        action.value = Event(MyPageActions.INFORMATION)
    }

    fun clickFollow(){
        action.value = Event(MyPageActions.FOLLOW)
    }

    fun clickPoint(){
        action.value = Event(MyPageActions.POINT)
    }

    fun clickInquiry(){
        action.value = Event(MyPageActions.INQUIRY)
    }

    fun clickCall(){
        action.value = Event(MyPageActions.CALL)
    }

    fun clickTerms(){
        action.value = Event(MyPageActions.TERMS)
    }


    enum class MyPageActions {
        SETTING, TERMS, BLOCK, FOLLOW, INFORMATION, POST, POINT, CALL, EDIT, INQUIRY

    }
}