package com.bravo.android.bravo.ui.view.main.community.together.fragment.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.util.Event

class TogetherSecondViewModel : BaseViewModel() {

    private val _action : MutableLiveData<Event<TogetherSecondAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherSecondAction>> get() = _action

    private val _listUri : MutableLiveData<MutableList<String?>> = MutableLiveData(mutableListOf())
    val listUrl : MutableLiveData<MutableList<String?>> get() = _listUri

    val uploadImg : ArrayList<String> = ArrayList()

    var photo : ArrayList<String> = ArrayList()

    val title : MutableLiveData<String> = MutableLiveData()


    private val _uploadHtml : MutableLiveData<String> = MutableLiveData()
    val uploadHtml : MutableLiveData<String> get() = _uploadHtml


    fun settingUriList(list : List<String>){
        val ss : ArrayList<String?> = arrayListOf()
        ss.addAll( _listUri.value!!)
        ss.addAll(list)
        _listUri.value = ss
    }

    fun settingUploadHtml(content : String){
        _uploadHtml.value = content
    }


    fun setOnGalleryAction(){
        _action.value = Event(TogetherSecondAction.GALLERY)
    }

    fun setOnNextAction(){
        _action.value = Event(TogetherSecondAction.NEXT)
    }


    enum class TogetherSecondAction{
        GALLERY, NEXT
    }

}