package com.bravo.android.bravo.ui.view.join.nickname

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.remote.source.AuthDataSource
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo
import java.util.regex.Pattern

class NicknameViewModel(

) : BaseViewModel() {

    val action: MutableLiveData<Event<NicknameActions>> = MutableLiveData()

    private val _nickname : MutableLiveData<String> = MutableLiveData()
    val nickname : MutableLiveData<String> get() = _nickname

    fun onNext(){
        action.value = Event(NicknameActions.NEXT)
    }

    enum class NicknameActions {
        NEXT
    }

}