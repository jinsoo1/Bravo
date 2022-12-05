package com.bravo.android.bravo.ui.view.join.address

import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.ui.view.join.nickname.NicknameViewModel
import com.bravo.android.bravo.util.Event

class AddressViewModel : BaseViewModel() {

    val action: MutableLiveData<Event<AddressActions>> = MutableLiveData()

    fun next(){
        action.value = Event(AddressActions.NEXT)
    }

    enum class AddressActions {
        NEXT
    }

}