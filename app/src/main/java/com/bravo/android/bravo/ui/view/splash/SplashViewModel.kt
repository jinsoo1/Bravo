package com.bravo.android.bravo.ui.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.local.pref.PreferencesController
import com.bravo.android.bravo.data.remote.source.AuthDataSource
import com.bravo.android.bravo.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class SplashViewModel(
    private val authDataSource: AuthDataSource
): BaseViewModel() {

    private var _action : MutableLiveData<Event<SplashAction>> = MutableLiveData()
    val action : LiveData<Event<SplashAction>> = _action
    init {
        if (PreferencesController.userInfoPref.accessToken.isNotBlank()) {
            authDataSource
                .validateAccessToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _action.value = Event(SplashAction.MOVE_MAIN)

                }, {
                    _action.value = Event(SplashAction.MOVE_LOGIN)
                    it.printStackTrace()
                })
                .addTo(compositeDisposable)
        } else {
            _action.value = Event(SplashAction.MOVE_LOGIN)
        }
    }
    enum class SplashAction{
        MOVE_LOGIN, MOVE_MAIN
    }
}