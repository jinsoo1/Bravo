package com.bravo.android.bravo.ui.view.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel

class JoinViewModel : BaseViewModel() {

    private val _userToken : MutableLiveData<String> = MutableLiveData()
    val userToken : LiveData<String> get() = _userToken

    private val _email : MutableLiveData<String> = MutableLiveData()
    val email : LiveData<String> get() = _email

    private val _type : MutableLiveData<String> = MutableLiveData()
    val type : LiveData<String> get() = _type

    private val _name : MutableLiveData<String> = MutableLiveData()
    val name : LiveData<String> get() = _name

    private val _regionToken : MutableLiveData<Int> = MutableLiveData()
    val region : LiveData<Int> get() = _regionToken

    private val _gender : MutableLiveData<Int> = MutableLiveData()
    val gender : LiveData<Int> get() = _gender

    private val _year : MutableLiveData<Int> = MutableLiveData()
    val year : LiveData<Int> get() = _year

    fun settingUser(token : String, email : String, type : String){
        _userToken.value = token
        _email.value = email
        _type.value = type
    }

    fun settingName(name : String){
        _name.value = name
    }

    fun settingRegionToken(address : Int){
        _regionToken.value = address
    }

    fun settingAddInfo(gender : Int, year : Int){
        _gender.value = gender
        _year.value = year
    }
}