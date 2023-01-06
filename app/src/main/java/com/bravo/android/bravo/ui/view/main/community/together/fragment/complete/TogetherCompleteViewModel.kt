package com.bravo.android.bravo.ui.view.main.community.together.fragment.complete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class TogetherCompleteViewModel : BaseViewModel() {

    private val _action : MutableLiveData<Event<TogetherCompleteAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherCompleteAction>> get() = _action




    fun setOnCompleteAction(){
        _action.value = Event(TogetherCompleteAction.COMPLETE)
    }

    enum class TogetherCompleteAction{
        COMPLETE
    }
}