package com.bravo.android.bravo.data.remote.source

import com.bravo.android.bravo.data.remote.api.AuthApi
import com.bravo.android.bravo.data.remote.model.DataResponse
import com.bravo.android.bravo.data.remote.model.VoidResponse
import com.bravo.android.bravo.data.remote.model.response.UserResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthDataSource(
    private val authApi: AuthApi
) {

    fun validateAccessToken(): Single<VoidResponse> {
        return authApi.validateAccessToken()
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun loginByKaKao(
        kakaoUserKey: String,
    ): Single<DataResponse<UserResponse>> {
        return authApi.loginByKaKao(kakaoUserKey)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(
        userToken : String,
        email : String,
        type : String,
        name : String,
        region : Int,
        gender : Int,
        year : Int
    ) : Single<UserResponse> {
        return authApi.login(userToken, email, type, name, region, gender, year)
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun compareVersion(version : Double) : Single<VoidResponse> {
        return authApi.compareVersion(version)
            .subscribeOn(Schedulers.io())
            .map { it }
            .observeOn(AndroidSchedulers.mainThread())
    }
}