/*
 * *
 *  * Created by Nguyễn Kim Khánh on 7/18/23, 10:10 AM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 7/18/23, 10:10 AM
 *
 */

package com.koai.base.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Dispatcher
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseApiController {
    companion object {
        const val TIME_OUT = 30L
    }

    fun getService(): Any {
        val baseUrl = getBaseUrl()

        val builder = okhttp3.OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val okHttpClient = builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .build()

        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(getApiService())
    }

    abstract fun getBaseUrl(): String

    abstract fun getApiService(): Class<*>
}
