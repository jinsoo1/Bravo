package com.bravo.android.bravo.ui.view.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class LoginViewModel : BaseViewModel() {
    private val _action: MutableLiveData<Event<LoginActions>> = MutableLiveData()
    val action : LiveData<Event<LoginActions>> get() = _action

    private val _loginType : MutableLiveData<String> = MutableLiveData()
    val loginType : LiveData<String> get() = _loginType

    fun getLoginData(type : String){

        if(type == "kakao"){
            _loginType.value = "카카오로 로그인 했었어요"

        }else if(type == "naver"){
            _loginType.value = "네이버로 로그인 했었어요"

        }

    }

    fun kakaoLogin(){
        _action.value = Event(LoginActions.KAKAO)
    }

    fun successLogin(){
        _action.value = Event(LoginActions.MAIN)
    }


    enum class LoginActions {
        KAKAO, GOOGLE, MAIN, PET
    }



}