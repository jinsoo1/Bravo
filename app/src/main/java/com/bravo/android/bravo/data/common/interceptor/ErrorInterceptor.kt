package com.bravo.android.bravo.data.common.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val response = chain.proceed(request)
//
//        when (response.code) {
//            401 -> {
//                val refreshResponse = try {
//                    authDataSource
//                        .refreshTokens(
//                            PreferencesController.userInfoPref.accessToken,
//                            PreferencesController.userInfoPref.refreshToken
//                        )
//                        .blockingGet()
//                } catch (e: Exception) {
//                    null
//                }
//
//                refreshResponse?.let {
//                    localDataSource.saveRefreshToken(it.refreshToken)
//                    localDataSource.saveAccessToken(it.accessToken)
//
//                    val builder = chain.request().newBuilder()
//                    builder.removeHeader("token")
//                    builder.addHeader("x-access-token", it.accessToken)
//                    builder.addHeader("x-refresh-token", it.refreshToken)
//                    return chain.proceed(builder.build())
//                } ?: run {
//                    return response
//                }
//            }
//            else -> return response
//        }
        val request = chain.request()
        val response = chain.proceed(request)

        // todo catch error and retry when code == 401
//        when (response.code) {
//            404 -> {
//               RxBus.sendEvent(Const.TOAST_ERROR, App.getString(R.string.error_404))
//            }
//        }
        return response
    }

}