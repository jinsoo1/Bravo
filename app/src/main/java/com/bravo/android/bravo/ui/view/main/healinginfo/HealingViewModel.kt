package com.bravo.android.bravo.ui.view.main.healinginfo

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.HealingDetailData
import com.bravo.android.bravo.data.common.model.HealingListData
import com.bravo.android.bravo.data.remote.source.HealingDataSource
import com.bravo.android.bravo.util.Event
import io.reactivex.rxkotlin.addTo

class HealingViewModel(
    private val healingDataSource: HealingDataSource
): BaseViewModel() {

    private val _action : MutableLiveData<Event<HealingAction>> = MutableLiveData()
    val action : LiveData<Event<HealingAction>> get() = _action

    private val _category : MutableLiveData<String> = MutableLiveData("주제별 보기")
    val category : MutableLiveData<String> get() = _category

    private val _healingList : MutableLiveData<List<HealingListData>> = MutableLiveData(listOf())
    val healingList : LiveData<List<HealingListData>> get() = _healingList

    private val _selectHealing : MutableLiveData<Event<HealingListData>> = MutableLiveData()
    val selectHealing : LiveData<Event<HealingListData>> get() = _selectHealing


    fun getHealingListData(){

        var category = _category.value

        if(_category.value == "주제별 보기") category = ""

        healingDataSource.getHealingListData(category ?: "")
            .map {
                it.map {
                    HealingListData(
                        it.infoToken,
                        it.img,
                        it.title,
                        it.comment,
                        it.shareCount,
                        it.likes,
                        it.isLiked()
                    )
                }
            }
            .subscribe({
                _healingList.value = it
            },{
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun onClickHealing(item : HealingListData){
        _selectHealing.value = Event(item)
    }

    fun settingCategory(category : String){
        _category.value = category
    }

    fun onClickBtnX(){
        _action.value = Event(HealingAction.MAIN_X)
    }

    fun likeItem(item : HealingListData){
        if(item.isLikeEnabled.get()){
            item.isLikeEnabled.set(false)

            val request = when(item.isLike.get()){
                true -> healingDataSource.unlikeHealingInfo(item.infoToken)
                false -> healingDataSource.likeHealingInfo(item.infoToken)
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
            1 -> _action.value = Event(HealingAction.HEALING)
            2 -> _action.value = Event(HealingAction.HUMOR)
            3 -> _action.value = Event(HealingAction.HEALTHY)
            4 -> _action.value = Event(HealingAction.HOBBY)
            5 -> _action.value = Event(HealingAction.TECHNOLOGY)
            6 -> _action.value = Event(HealingAction.ETC)
            7 -> _action.value = Event(HealingAction.X)
        }
    }

    enum class HealingAction{
        HEALING, HUMOR, HEALTHY, HOBBY, TECHNOLOGY, ETC, X, MAIN_X
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindHealingList")
        fun bindHealingList(view: RecyclerView, list: List<HealingListData>) {
            val adapter = view.adapter as HealingListAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }
    }
}