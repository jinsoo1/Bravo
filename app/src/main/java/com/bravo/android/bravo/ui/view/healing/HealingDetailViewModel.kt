package com.bravo.android.bravo.ui.view.healing

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.App.Companion.toast
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.HealingCommentListData
import com.bravo.android.bravo.data.common.model.HealingDetailData
import com.bravo.android.bravo.data.common.model.HealingListData
import com.bravo.android.bravo.data.remote.source.HealingDataSource
import com.bravo.android.bravo.ui.view.main.healinginfo.HealingListAdapter
import com.bravo.android.bravo.util.Event
import com.bravo.android.bravo.util.ext.StringExt.regionSlice
import com.bravo.android.bravo.util.ext.StringExt.yearSlice
import com.bravo.android.bravo.util.ext.toRequestBody
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HealingDetailViewModel(
    private val healingDataSource: HealingDataSource
) : BaseViewModel() {

    private val _action : MutableLiveData<Event<HealingDetailAction>> = MutableLiveData()
    val action : LiveData<Event<HealingDetailAction>> get() = _action

    private val _infoToken : MutableLiveData<String> = MutableLiveData()
    val infoToken : LiveData<String> get() = _infoToken

    private val _detailData : MutableLiveData<HealingDetailData> = MutableLiveData()
    val detailData : LiveData<HealingDetailData> get() = _detailData

    private val _commentList : MutableLiveData<List<HealingCommentListData>> = MutableLiveData(listOf())
    val commentList : LiveData<List<HealingCommentListData>> get() = _commentList

    val comment : MutableLiveData<String> = MutableLiveData("")

    private val _commentImg : MutableLiveData<String> = MutableLiveData()
    val commentImg : MutableLiveData<String> get() = _commentImg

    var photo : String? = null

    private val _userProfile : MutableLiveData<HealingCommentListData> = MutableLiveData()
    val userProfile : LiveData<HealingCommentListData> get() = _userProfile

    fun settingInfoToken(token : String){
        _infoToken.value = token
    }

    fun getDetailHealingData(){
        healingDataSource.getDetailHealingData(infoToken.value!!)
            .subscribe({
                _detailData.value = HealingDetailData(
                    it.infoToken,
                    it.title,
                    it.content,
                    it.img,
                    it.category,
                    it.createdAt,
                    it.beforeHi,
                    it.afterHi,
                    it.comment,
                    it.shareCount,
                    it.likes,
                    it.isLiked()
                )
                Log.d("asdasf123", it.toString())
            },{
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    fun getHealingCommentData(){
        healingDataSource.getHealingCommentData(infoToken.value!!)
            .map {
                it.map {
                    HealingCommentListData(
                        it.infoToken,
                        it.comment,
                        it.photo,
                        it.userToken,
                        it.profileImg,
                        it.name,
                        yearSlice(it.year),
                        it.gender == 1,
                        regionSlice(it.sido),
                        it.sigungu,
                        it.createdAt,
                        it.isBlocked == 1
                    )
                }
            }
            .subscribe({
                _commentList.value = it
                Log.d("asdq23qsd", it.toString())
            },{
                Log.d("asdq23qsd E", it.toString())
            })
            .addTo(compositeDisposable)

    }

    fun postCommentToHealing(){

        val file = if(photo != null){
            val file = File(photo)
            MultipartBody.Part.createFormData(
                "commentPhoto", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        } else null


        healingDataSource.postCommentToHealing(
            infoToken.value!!.toRequestBody(),
            comment.value!!.toRequestBody(),
            file
        )
            .subscribe({
                CoroutineScope(Dispatchers.Main).launch {
                    comment.value = ""
                    _commentImg.value = null
                    photo = null
                }
                CoroutineScope(Dispatchers.IO).launch {
                    getHealingCommentData()
                }
                Log.d("postCommentToHealing", it.toString())
            },{
                Log.d("postCommentToHealing E", it.toString())
            })
            .addTo(compositeDisposable)
    }

    fun onClickBeforeContent(infoToken : String?){
        if(infoToken == null){
            toast("이전 힐링정보가 없습니다.")?.show()
            return
        }

        _infoToken.value = infoToken
    }

    fun onClickAfterContent(infoToken : String?){
        if(infoToken == null){
            toast("다음 힐링정보가 없습니다.")?.show()
            return
        }

        _infoToken.value = infoToken
    }

    fun likeItem(){
        if(detailData.value!!.isLikeEnabled.get()){
            detailData.value!!.isLikeEnabled.set(false)

            val request = when(detailData.value!!.isLike.get()){
                true -> healingDataSource.unlikeHealingInfo(detailData.value!!.infoToken)
                false -> healingDataSource.likeHealingInfo(detailData.value!!.infoToken)
            }

            request
                .doOnError{
                    detailData.value!!.isLikeEnabled.set(true)
                }
                .subscribe( {
                    detailData.value!!.isLike.set(detailData.value!!.isLike.get().not())
                    detailData.value!!.isLikeEnabled.set(true)
                    getDetailHealingData()
                },{
                    Log.d("asd123asr E ", it.toString())
                })
                .addTo(compositeDisposable)
        }
    }

    fun setOnSendComment(){
        _action.value = Event(HealingDetailAction.SEND)
    }
    fun setOnOpenGallery(){
        _action.value = Event(HealingDetailAction.GALLERY)
    }
    fun setOnClearImage(){
        _action.value = Event(HealingDetailAction.CLEAR)
    }

    fun setUserProfileData(item : HealingCommentListData){
        _userProfile.value = item
    }
    fun setReportAction(){
        _action.value = Event(HealingDetailAction.REPORT)
    }
    fun setBanAction(){
        _action.value = Event(HealingDetailAction.BAN)
    }

    enum class HealingDetailAction{
        SEND, GALLERY, CLEAR, REPORT, BAN
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindHealingCommentList")
        fun bindHealingCommentList(view: RecyclerView, list: List<HealingCommentListData>) {
            val adapter = view.adapter as HealingCommentList?
            adapter?.let {
                it.updateItems(list)
            }
        }
    }

}