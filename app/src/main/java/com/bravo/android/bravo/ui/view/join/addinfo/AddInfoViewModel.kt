package com.bravo.android.bravo.ui.view.join.addinfo

import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel

class AddInfoViewModel : BaseViewModel() {

    val gender: MutableLiveData<Boolean> = MutableLiveData(false) //false이면 남자 true이면 여자

    fun next(){

    }

}