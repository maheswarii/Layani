package com.yps.layani.admin.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object ApiWorker {
    private var mClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null

    val client: OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor { chain ->
                        val original = chain.request()

                        val requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")

                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                mClient = httpBuilder.build()

            }
            return mClient!!
        }


    val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverter!!
        }
}
