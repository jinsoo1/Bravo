package com.bravo.android.bravo.data.local.pref

interface BDPreferences {
    interface UserInfo {
        var accessToken: String
        var refreshToken: String

        var userToken: String
        var email: String
        var name: String
        var userType: String
        var profileImg : String?
        var region : Int
        var gender : Boolean
        var year : Int



        var noticeCreated : String

        fun clearPref()
    }

    interface Permanent {
    }
}