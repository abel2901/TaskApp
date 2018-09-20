package com.company.task

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient


class MyApplication : Application() {

    companion object {
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

}
