package com.bravo.android.bravo.ui.view.join.nickname

import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class NicknameViewModel : BaseViewModel() {

    val action: MutableLiveData<Event<NicknameActions>> = MutableLiveData()

    fun next(){
        action.value = Event(NicknameActions.NEXT)
    }

    enum class NicknameActions {
        NEXT
    }

}