package com.bravo.android.bravo.ui.view.join.terms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.remote.model.response.UserResponse
import com.bravo.android.bravo.data.remote.source.AuthDataSource
import com.bravo.android.bravo.data.remote.source.UserDataSource
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo

class TermsViewModel(
    private val authDataSource: AuthDataSource
) : BaseViewModel() {

    private val _action : MutableLiveData<Event<TermsAction>> = MutableLiveData()
    val action : LiveData<Event<TermsAction>> get() = _action

    private val _userData : MutableLiveData<UserResponse> = MutableLiveData()
    val userData : LiveData<UserResponse> get() = _userData

    fun loginData(userToken : String, email : String, type : String, name : String, region : Int, gender : Int, year : Int){
        authDataSource.login(userToken, email, type, name, region, gender, year)
            .subscribe({
                Log.d("loginData", it.toString())
                _userData.value = it
            },{
                Log.d("loginData E", it.toString())
            })
            .addTo(compositeDisposable)
    }

    fun onNext(){
        _action.value = Event(TermsAction.NEXT)
    }

    fun onSignIn(){
        _action.value = Event(TermsAction.SING_IN)
    }

    enum class TermsAction{
        NEXT, SING_IN
    }
}