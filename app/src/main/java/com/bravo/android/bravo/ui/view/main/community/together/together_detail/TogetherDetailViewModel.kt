package com.bravo.android.bravo.ui.view.main.community.together.together_detail

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.TogetherDetailData
import com.bravo.android.bravo.data.common.model.TogetherUserListData
import com.bravo.android.bravo.data.remote.source.TogetherDataSource
import com.bravo.android.bravo.util.Event
import com.bravo.android.bravo.util.ext.StringExt.regionSlice
import com.bravo.android.bravo.util.ext.StringExt.yearSlice
import io.reactivex.rxkotlin.addTo

class TogetherDetailViewModel(
    private val togetherDataSource: TogetherDataSource
) : BaseViewModel() {

    private val _action : MutableLiveData<Event<TogetherDetailAction>> = MutableLiveData()
    val action : LiveData<Event<TogetherDetailAction>> get() = _action

    private val _togetherToken : MutableLiveData<String> = MutableLiveData()
    val togetherToken : LiveData<String> get() = _togetherToken

    private val _togetherDetail : MutableLiveData<TogetherDetailData> = MutableLiveData()
    val togetherDetail : LiveData<TogetherDetailData> get() = _togetherDetail

    private val _userList : MutableLiveData<List<TogetherUserListData>> = MutableLiveData(listOf())
    val userList : LiveData<List<TogetherUserListData>> get() = _userList

    fun settingTogetherToken(token : String){
        _togetherToken.value = token
    }

    fun getTogetherDetail(token: String){
        togetherDataSource.getTogetherDetail(token)
            .subscribe({
                Log.d("asdfasdfq12", it.toString())
                _togetherDetail.value = TogetherDetailData(
                    it.togetherToken,
                    it.profileImg,
                    it.name,
                    yearSlice(it.year),
                    it.gender == 1,
                    regionSlice(it.sido),
                    it.sigungu,
                    it.createdAt,
                    it.title,
                    it.content,
                    it.peopleNum,
                    it.location == 1,
                    it.locationDetail,
                    it.togetherGender,
                    it.ageState == 1,
                    it.firstAge,
                    it.secondAge,
                    it.activityState == 1,
                    it.date,
                    it.category,
                    it.likeCount,
                    it.peopleCount,
                    it.isAuth == 1
                )
            },{
                Log.d("asdfasdfq12 E ", it.toString())
            })
            .addTo(compositeDisposable)
    }

    fun getUserList(token: String){
        togetherDataSource.getUserListData(token)
            .map {
                it.map {
                    TogetherUserListData(
                        it.userToken,
                        it.profileImg,
                        it.gender == 1
                    )
                }
            }
            .subscribe({
                _userList.value = it
            },{
                Log.d("asdasdfqwe E", it.toString())
            })
            .addTo(compositeDisposable)
    }

    fun setJoinTogetherBottomSheetShow(){
        _action.value = Event(TogetherDetailAction.BOTTOM_SHEET)
    }
    fun setDismissJoinTogetherBottomSheet(){
        _action.value = Event(TogetherDetailAction.DISMISS)
    }
    fun setTogetherJoin(){
        _action.value = Event(TogetherDetailAction.JOIN)
    }

    enum class TogetherDetailAction(){
        BOTTOM_SHEET, DISMISS, JOIN
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindTogetherUserList")
        fun bindTogetherUserList(rv: RecyclerView, list: List<TogetherUserListData>) {
            val adapter = rv.adapter as TogetherUserListAdapter?
            adapter?.let {
                it.updateItems(list)
            }
        }

    }

}