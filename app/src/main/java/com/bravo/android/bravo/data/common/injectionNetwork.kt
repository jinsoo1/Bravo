package com.bravo.android.bravo.data.common

import android.util.Log
import androidx.databinding.ktx.BuildConfig
import com.bravo.android.bravo.data.common.interceptor.ErrorInterceptor
import com.bravo.android.bravo.data.common.interceptor.HeaderInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        HttpLoggingInterceptor(LoggerInterceptor()).apply {
            level = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor.Level.BODY
            }else{
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single { HeaderInterceptor() }

    single { ErrorInterceptor() }

    single {
        GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<HeaderInterceptor>())
            .addInterceptor(get<ErrorInterceptor>())
            .build()
    }

    single { RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()) }

    single { GsonConverterFactory.create() }

    single {
        Retrofit.Builder()
            .baseUrl("")
            .client(get<OkHttpClient>())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }


}

class LoggerInterceptor : HttpLoggingInterceptor.Logger{
    companion object {
        const val LOG_DIVIDER = "================================================================"
    }

    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun log(message: String) {
        val trimMessage = message.trim { it <= ' ' }
        if (trimMessage.startsWith("{") && trimMessage.endsWith("}")
            || trimMessage.startsWith("[") && trimMessage.endsWith("]")
        ) {
            try {
                val prettyPrintJson = gson.toJson(JsonParser().parse(message))
                Log.e("Network Response", LOG_DIVIDER + "\n" + prettyPrintJson + "\n" + LOG_DIVIDER)
            } catch (e: Exception) {
                Log.d("Network Response", message, null)
            }
        } else {
            Log.e("Network Response", message, null)
        }
    }
}