package com.bravo.android.bravo.data.remote

import android.service.autofill.UserData
import com.bravo.android.bravo.data.remote.source.*
import org.koin.dsl.module
import kotlin.math.sin

val remoteDataSourceModule = module {


    //DataSource추가될때마다 한줄씩 추가
    single { AuthDataSource(get()) }
    single { UserDataSource(get()) }
    single { HealingDataSource(get()) }
    single { StoryDataSource(get()) }
    single { TogetherDataSource(get()) }


}