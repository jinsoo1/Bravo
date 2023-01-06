package com.bravo.android.bravo.ui.view.main.community.story.story_detail

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bravo.android.bravo.base.App
import com.bravo.android.bravo.base.BaseViewModel
import com.bravo.android.bravo.data.common.model.HealingCommentListData
import com.bravo.android.bravo.data.common.model.StoryCommentListData
import com.bravo.android.bravo.data.common.model.StoryDetailData
import com.bravo.android.bravo.data.remote.model.response.StoryCommentListResponse
import com.bravo.android.bravo.data.remote.source.StoryDataSource
import com.bravo.android.bravo.ui.view.healing.HealingCommentList
import com.bravo.android.bravo.ui.view.healing.HealingDetailViewModel
import com.bravo.android.bravo.util.Event
import com.bravo.android.bravo.util.ext.StringExt.calculationTime
import com.bravo.android.bravo.util.ext.StringExt.regionSlice
import com.bravo.android.bravo.util.ext.StringExt.yearSlice
import com.bravo.android.bravo.util.ext.toRequestBody
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class StoryDetailViewModel(
    private val storyDataSource: StoryDataSource
) : BaseViewModel() {

    private val _action : MutableLiveData<Event<StoryDetailAction>> = MutableLiveData()
    val action : LiveData<Event<StoryDetailAction>> get() = _action

    private val _feedToken : MutableLiveData<String> = MutableLiveData()
    val feedToken : LiveData<String> get() = _feedToken

    private val _detailData : MutableLiveData<StoryDetailData> = MutableLiveData()
    val detailData : LiveData<StoryDetailData> get() = _detailData

    val comment : MutableLiveData<String> = MutableLiveData("")

    private val _isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading : LiveData<Boolean> get() = _isLoading

    private val _commentImg : MutableLiveData<String> = MutableLiveData()
    val commentImg : MutableLiveData<String> get() = _commentImg

    private val _commentList : MutableLiveData<List<StoryCommentListData>> = MutableLiveData(listOf())
    val commentList : LiveData<List<StoryCommentListData>> get() = _commentList

    var photo : String? = null

    fun settingFeedToken(token : String){
        _feedToken.value = token
    }


    fun getDetailStoryData(){
        _isLoading.value = true
        storyDataSource.getDetailStoryData(_feedToken.value!!)
            .subscribe({
                _detailData.value = StoryDetailData(
                    it.profileImg,
                    it.name,
                    yearSlice(it.year),
                    regionSlice(it.sido),
                    it.sigungu,
                    calculationTime(it.createdAt),
                    it.content,
                    it.feedToken,
                    it.likeCount,
                    it.commentCount,
                    it.shareCount,
                    it.beforeS,
                    it.afterS,
                    it.isLiked()
                )
                Log.d("asd123124asd", it.toString())

            },{
                it.printStackTrace()
            })
            .addTo(compositeDisposable)

    }

    fun setProgress(){
        _isLoading.value = false
    }

    fun postCommentToStory(){
        val file = if(photo != null){
            val file = File(photo)
            MultipartBody.Part.createFormData(
                "commentPhoto", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        } else null

        storyDataSource.postCommentToStory(
            _feedToken.value!!.toRequestBody(),
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
                    getStoryCommentData()
                }
                Log.d("postCommentToHealing", it.toString())
            },{
                Log.d("postCommentToHealing E", it.toString())
            })
            .addTo(compositeDisposable)

    }

    fun getStoryCommentData(){
        storyDataSource.getStoryCommentData(feedToken.value!!)
            .map {
                it.map {
                    StoryCommentListData(
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

    fun likeItem(){
        if(detailData.value!!.isLikeEnabled.get()){
            detailData.value!!.isLikeEnabled.set(false)

            val request = when(detailData.value!!.isLike.get()){
                true -> storyDataSource.unlikeStory(detailData.value!!.feedToken)
                false -> storyDataSource.likeStory(detailData.value!!.feedToken)
            }

            request
                .doOnError{
                    detailData.value!!.isLikeEnabled.set(true)
                }
                .subscribe( {

                    if(detailData.value!!.isLike.get()) {
                        detailData.value!!.isLikeCount.set(detailData.value!!.isLikeCount.get() - 1)
                    }else{
                        detailData.value!!.isLikeCount.set(detailData.value!!.isLikeCount.get() + 1)
                    }

                    detailData.value!!.isLike.set(detailData.value!!.isLike.get().not())
                    detailData.value!!.isLikeEnabled.set(true)

                },{
                    Log.d("asd123asr E ", it.toString())
                })
                .addTo(compositeDisposable)
        }
    }



    fun onClickBeforeContent(feedToken : String?){
        if(feedToken == null){
            App.toast("이전 이야기가 없습니다.")?.show()
            return
        }

        _feedToken.value = feedToken
    }

    fun onClickAfterContent(feedToken : String?){
        if(feedToken == null){
            App.toast("다음 이야기가 없습니다.")?.show()
            return
        }

        _feedToken.value = feedToken
    }

    fun setSendComment(){
        _action.value = Event(StoryDetailAction.SEND)
    }

    fun setOpenGallery(){
        _action.value = Event(StoryDetailAction.GALLERY)
    }

    fun setOnClearImage(){
        _commentImg.value = null
        photo = null
    }

    enum class StoryDetailAction{
        GALLERY, SEND
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bindStoryCommentList")
        fun bindStoryCommentList(view: RecyclerView, list: List<StoryCommentListData>) {
            val adapter = view.adapter as StoryCommentList?
            adapter?.let {
                it.updateItems(list)
            }
        }
    }

}