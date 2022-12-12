package com.bravo.android.bravo.data.remote.source

import com.bravo.android.bravo.data.remote.api.UserApi
import com.bravo.android.bravo.data.remote.model.response.RegionResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserDataSource(
    private val userApi: UserApi
) {

    fun getRegion(
        sido : String
    ) : Single<List<RegionResponse>>{
        return userApi.getRegion(sido)
            .subscribeOn(Schedulers.io())
            .map {it.data}
            .observeOn(AndroidSchedulers.mainThread())

    }


}