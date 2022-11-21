package com.bravo.android.bravo.data.common.interceptor

import com.bravo.android.bravo.data.local.pref.PreferencesController
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val token = PreferencesController.userInfoPref.accessToken
        val refreshToken = PreferencesController.userInfoPref.refreshToken
        builder.removeHeader("token")
        builder.addHeader("x-access-token", token)
        builder.addHeader("x-refresh-token", refreshToken)
        return chain.proceed(builder.build())
    }
}