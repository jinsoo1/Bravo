package com.bravo.android.bravo.ui.view.main.community.story.story_write

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.remote.source.StoryDataSource
import com.bravo.android.bravo.util.Event

class StoryWriteViewModel(
    private val storyDataSource: StoryDataSource
) : BaseViewModel() {

    private val _action : MutableLiveData<Event<StoryWriteAction>> = MutableLiveData()
    val action : LiveData<Event<StoryWriteAction>> get() = _action

    private val _listUri : MutableLiveData<MutableList<String?>> = MutableLiveData(mutableListOf())
    val listUrl : MutableLiveData<MutableList<String?>> get() = _listUri

    val url: ArrayList<String> = ArrayList()

    private val _deleteUri : MutableLiveData<Event<String>> = MutableLiveData()
    val deleteUri : LiveData<Event<String>> get() = _deleteUri

    private val _uploadHtml : MutableLiveData<String> = MutableLiveData()
    val uploadHtml : MutableLiveData<String> get() = _uploadHtml

    var photo : ArrayList<String> = ArrayList()



    fun settingUriList(list : List<String>){
        val ss : ArrayList<String?> = arrayListOf()
        ss.addAll( _listUri.value!!)
        ss.addAll(list)
        _listUri.value = ss
    }

    fun settingUploadHtml(content : String){
        _uploadHtml.value = content
    }

    fun deleteImage(position : Int){
        val list : ArrayList<String?> = arrayListOf()
        list.addAll( _listUri.value!!)
        list.removeAt(position)
        photo.removeAt(position)

        _deleteUri.value = Event(listUrl.value!![position]!!)

        _listUri.value = list
    }

    fun onClickUpload(){
        _action.value = Event(StoryWriteAction.UPLOAD)
    }


    enum class StoryWriteAction{
        UPLOAD
    }


    companion object {
        @JvmStatic
        @BindingAdapter("bindImageList")
        fun bindImageList(rv: RecyclerView, list: List<String>) {
            val adapter = rv.adapter as ImageListAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }

    }
}