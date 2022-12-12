package com.bravo.android.bravo.data.local

import com.bravo.android.bravo.data.common.model.User
import com.bravo.android.bravo.data.local.pref.PreferencesController.userInfoPref

class UserLoginLocalDataSource {

    fun saveLoginInfo(
        user: User
    ) {
        userInfoPref.apply {
            userToken = user.userToken
            email = user.email
            name = user.name
            userType = user.userType
            profileImg = user.profileImg
            region = user.region
            gender = user.gender
            year = user.year
        }
    }


    fun saveAccessToken(accessToken: String){
        userInfoPref.accessToken = accessToken
    }

    fun saveRefreshToken(refreshToken: String){
        userInfoPref.refreshToken = refreshToken
    }

    fun clearPref() {
        userInfoPref.clearPref()
    }

}