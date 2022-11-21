package com.bravo.android.bravo.base

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.bravo.android.bravo.data.common.networkModule
import com.bravo.android.bravo.data.local.localDataSourceModule
import com.bravo.android.bravo.data.remote.remoteDataSourceModule
import com.bravo.android.bravo.ui.view.viewModelModule
import com.bravo.android.bravo.util.ext.setupKoin
import org.jetbrains.anko.toast

class App : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        setKoin()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

//        KakaoSdk.init(this, getString(R.string.native_app_key))

    }

    private fun setKoin(){
        setupKoin(
            this,
            networkModule,
            remoteDataSourceModule,
            localDataSourceModule,
            viewModelModule
        )
    }


    companion object{
        lateinit var appContext : Context

        fun getString(@StringRes resId: Int): String {
            return appContext.getString(resId)
        }

        fun toast(msg: String) = App.appContext.toast(msg)

        fun checkInternetConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

}