package com.bravo.android.bravo.data.local.pref

interface BDPreferences {
    interface UserInfo {
        var accessToken: String
        var refreshToken: String

        var userType: String
        var userToken: String
        var nickname: String
        var email: String
        var profileImg : String?
        var agree : Boolean



        var noticeCreated : String

        fun clearPref()
    }

    interface Permanent {
    }
}