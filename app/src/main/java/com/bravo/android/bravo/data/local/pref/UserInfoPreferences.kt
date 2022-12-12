package com.bravo.android.bravo.data.local.pref

import android.content.Context
import androidx.core.content.edit

class UserInfoPreferences(
    applicationContext: Context
) : BDPreferences.UserInfo {

    private val userInfoPref =
        applicationContext.getSharedPreferences(PREF_USER_INFO, Context.MODE_PRIVATE)

    override var accessToken: String
        get() = userInfoPref.getString(KEY_ACCESS_TOKEN, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_ACCESS_TOKEN, value) } }
    override var refreshToken: String
        get() = userInfoPref.getString(KEY_REFRESH_TOKEN, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_REFRESH_TOKEN, value) } }

    override var userType: String
        get() = userInfoPref.getString(KEY_USER_TYPE, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_USER_TYPE, value) } }
    override var userToken: String
        get() = userInfoPref.getString(KEY_USER_TOKEN, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_USER_TOKEN, value) } }
    override var name: String
        get() = userInfoPref.getString(KEY_NAME, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_NAME, value) } }
    override var email: String
        get() = userInfoPref.getString(KEY_EMAIL, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_EMAIL, value) } }
    override var profileImg: String?
        get() = userInfoPref.getString(KEY_PROFILEIMG, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_PROFILEIMG, value) } }
    override var region: Int
        get() = userInfoPref.getInt(KEY_REGION, 0)
        set(value) { userInfoPref.edit { putInt(KEY_REGION, value) } }
    override var gender: Int
        get() = userInfoPref.getInt(KEY_GENDER, 0)
        set(value) { userInfoPref.edit { putInt(KEY_GENDER, value) } }
    override var year: Int
        get() = userInfoPref.getInt(KEY_YEAR, 0)
        set(value) { userInfoPref.edit { putInt(KEY_YEAR, value) } }


    override var noticeCreated: String
        get() = userInfoPref.getString(KEY_NOTICE_CREATED, null) ?: ""
        set(value) { userInfoPref.edit { putString(KEY_NOTICE_CREATED, value) } }


    override fun clearPref() {
        userInfoPref.edit { clear() }
    }

    companion object {
        private const val PREF_USER_INFO = "pref_user_info"

        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_TYPE = "user_type"
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PROFILEIMG = "profileImg"
        private const val KEY_REGION = "region"
        private const val KEY_GENDER = "gender"
        private const val KEY_YEAR = "year"

        private const val KEY_NOTICE_CREATED = "notice_created"
    }
}