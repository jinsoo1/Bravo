package com.bravo.android.bravo.data.remote.api

import com.bravo.android.bravo.data.remote.model.DataResponse
import com.bravo.android.bravo.data.remote.model.response.RegionResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("/user/region")
    fun getRegion(
        @Query("sido") sido: String
    ): Single<DataResponse<List<RegionResponse>>>

}