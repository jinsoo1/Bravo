package com.bravo.android.bravo.data.local

import com.bravo.android.bravo.data.local.pref.BDPreferences
import com.bravo.android.bravo.data.local.pref.PermanentPreferences
import com.bravo.android.bravo.data.local.pref.UserInfoPreferences
import org.koin.dsl.module

val localDataSourceModule = module {
    single<BDPreferences.UserInfo> { UserInfoPreferences(get()) }
    single<BDPreferences.Permanent> { PermanentPreferences(get()) }

    single<UserLoginLocalDataSource> { UserLoginLocalDataSource() }
}