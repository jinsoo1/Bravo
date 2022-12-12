package com.bravo.android.bravo.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.bravo.android.bravo.R
import com.bravo.android.bravo.data.common.networkModule
import com.bravo.android.bravo.data.local.localDataSourceModule
import com.bravo.android.bravo.data.remote.remoteDataSourceModule
import com.bravo.android.bravo.databinding.ToastPublicBinding
import com.bravo.android.bravo.ui.view.viewModelModule
import com.bravo.android.bravo.util.ext.setupKoin
import com.kakao.sdk.common.KakaoSdk
import org.jetbrains.anko.toast

class App : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        setKoin()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        KakaoSdk.init(this, "79e1b2c2148f7da5f97085d2930e0a45")

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

        fun toast(msg: String) : Toast?{
            val inflater = LayoutInflater.from(appContext)
            val binding = DataBindingUtil.inflate<ToastPublicBinding>(inflater, R.layout.toast_public, null, false)
            binding.tvSample.text = msg

            return Toast(appContext).apply {
                setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
                duration = Toast.LENGTH_SHORT
                view = binding.root
            }
        }

        private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

        fun checkInternetConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

}