package com.bravo.android.bravo.data.remote.source

import com.bravo.android.bravo.data.remote.api.StoryApi
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.HealingCommentListResponse
import com.bravo.android.bravo.data.remote.model.response.StoryCommentListResponse
import com.bravo.android.bravo.data.remote.model.response.StoryDetailResponse
import com.bravo.android.bravo.data.remote.model.response.StoryListResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryDataSource(
    private val storyApi: StoryApi
) {

    fun getStoryListData(
        category : String
    ) : Single<List<StoryListResponse>>{
        return storyApi.getStoryListData(category)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun storyUpload(
        content : RequestBody,
        category: RequestBody,
        uploadImg: List<RequestBody>,
        photo: List<MultipartBody.Part>
    ) : Single<VoidResponse> {
        return storyApi.storyUpload(content, category, uploadImg, photo)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailStoryData(
        feedToken : String
    ) : Single<StoryDetailResponse>{
        return storyApi.getDetailHealingData(feedToken)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun postCommentToStory(
        feedToken: RequestBody,
        comment : RequestBody,
        commentImg :  MultipartBody.Part?
    ) : Single<VoidResponse>{
        return storyApi.postCommentToHealing(feedToken, comment, commentImg)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getStoryCommentData(
        feedToken: String
    ) : Single<List<StoryCommentListResponse>>{
        return storyApi.getStoryCommentData(feedToken)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun likeStory(
        feedToken: String
    ) : Single<VoidResponse>{
        return storyApi.likeStory(feedToken)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun unlikeStory(
        feedToken: String
    ) : Single<VoidResponse>{
        return storyApi.unlikeStory(feedToken)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }
}