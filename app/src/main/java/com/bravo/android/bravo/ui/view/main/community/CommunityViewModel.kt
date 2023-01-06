package com.bravo.android.bravo.ui.view.main.community

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.HealingListData
import com.bravo.android.bravo.data.common.model.StoryListData
import com.bravo.android.bravo.data.remote.source.StoryDataSource
import com.bravo.android.bravo.ui.view.main.healinginfo.HealingListAdapter
import com.bravo.android.bravo.ui.view.main.healinginfo.HealingViewModel
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.runBlocking

class CommunityViewModel(
    private val storyDataSource: StoryDataSource
): BaseViewModel() {

    private val _actionBts : MutableLiveData<Event<CommunityAction>> = MutableLiveData()
    val actionBts : LiveData<Event<CommunityAction>> get() = _actionBts

    private val _actionWrite : MutableLiveData<Event<WriteAction>> = MutableLiveData()
    val actionWrite : LiveData<Event<WriteAction>> get() = _actionWrite

    private val _category : MutableLiveData<String> = MutableLiveData("주제별 보기")
    val category : MutableLiveData<String> get() = _category

    private val _write : MutableLiveData<Event<String>> = MutableLiveData()
    val write : MutableLiveData<Event<String>> get() = _write

    private val _storyList : MutableLiveData<List<StoryListData>> = MutableLiveData(listOf())
    val storyList : LiveData<List<StoryListData>> get() = _storyList

    private val _action : MutableLiveData<Event<StoryListData>> = MutableLiveData()
    val action : LiveData<Event<StoryListData>> get() = _action

    private val _filterState : MutableLiveData<Boolean> = MutableLiveData(false)
    val filterState : LiveData<Boolean> get() = _filterState


    fun getStoryListData(){

        var category = _category.value

        if(_category.value == "주제별 보기") category = ""

        storyDataSource.getStoryListData(category ?: "")
            .map {
                it.map {
                    StoryListData(
                        it.token,
                        it.profileImg,
                        it.name,
                        it.year,
                        it.gender,
                        it.sido,
                        it.sigungu,
                        it.createdAt,
                        it.content,
                        it.Photo,
                        it.LikeCount,
                        it.Count2,
                        it.Count3,
                        it.isLiked(),
                        it.state == 1,
                        it.title,
                        it.peopleNum,
                        it.ageState == 1,
                        it.firstAge,
                        it.secondAge,
                        it.togetherGender,
                        it.activityState == 1,
                        it.date,
                        it.category
                    )
                }
            }
            .subscribe({
                _storyList.value = it
                Log.d("getStoryListData", it.toString())
            },{
                Log.d("getStoryListData E", it.toString())
            })
            .addTo(compositeDisposable)
    }

    fun onClickItem(item : StoryListData){
        _action.value = Event(item)
    }

    fun settingCategory(category : String){
        _category.value = category
    }
    fun settingWrite(write : String){
        _write.value = Event(write)
    }

    fun onClickBtnX(){
        _actionBts.value = Event(CommunityAction.MAIN_X)
    }

    fun likeItem(item : StoryListData){
        if(item.isLikeEnabled.get()){
            item.isLikeEnabled.set(false)

            val request = when(item.isLike.get()){
                true -> storyDataSource.unlikeStory(item.token)
                false -> storyDataSource.likeStory(item.token)
            }

            request
                .doOnError{
                    item.isLikeEnabled.set(true)
                }
                .subscribe( {

                    if(item.isLike.get()) {
                        item.isLikeCount.set(item.isLikeCount.get() - 1)
                    }else{
                        item.isLikeCount.set(item.isLikeCount.get() + 1)
                    }

                    item.isLike.set(item.isLike.get().not())
                    item.isLikeEnabled.set(true)

                },{
                    Log.d("asd123asr E ", it.toString())
                })
                .addTo(compositeDisposable)
        }
    }

    fun onBottomSheetClick(item : Int){
        when(item){
            1 -> _actionBts.value = Event(CommunityAction.EXERCISE)
            2 -> _actionBts.value = Event(CommunityAction.HOBBY)
            3 -> _actionBts.value = Event(CommunityAction.SOCIAL)
            4 -> _actionBts.value = Event(CommunityAction.FAMOUS)
            5 -> _actionBts.value = Event(CommunityAction.PET)
            6 -> _actionBts.value = Event(CommunityAction.ETC)
            7 -> _actionBts.value = Event(CommunityAction.X)
        }
    }

    fun onWriteBottomSheetClick(item : Int){
        when(item){
            1 -> _actionWrite.value = Event(WriteAction.EXERCISE)
            2 -> _actionWrite.value = Event(WriteAction.HOBBY)
            3 -> _actionWrite.value = Event(WriteAction.SOCIAL)
            4 -> _actionWrite.value = Event(WriteAction.FAMOUS)
            5 -> _actionWrite.value = Event(WriteAction.PET)
            6 -> _actionWrite.value = Event(WriteAction.ETC)
            7 -> _actionWrite.value = Event(WriteAction.X)
        }
    }

    fun setOnFilterState(){
        _filterState.value = !_filterState.value!!
    }

    enum class CommunityAction{
        EXERCISE, HOBBY, SOCIAL, FAMOUS, PET, ETC, X, MAIN_X
    }

    enum class WriteAction{
        EXERCISE, HOBBY, SOCIAL, FAMOUS, PET, ETC, X
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindStoryList")
        fun bindStoryList(view: RecyclerView, list: List<StoryListData>) {
            val adapter = view.adapter as StoryListAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }
    }

}