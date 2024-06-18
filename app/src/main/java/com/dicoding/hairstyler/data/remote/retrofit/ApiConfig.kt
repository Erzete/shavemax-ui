package com.dicoding.hairstyler.data.remote.retrofit

import com.dicoding.hairstyler.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {

        private const val BASE_URL_ONE = BuildConfig.BASE_URL_ONE
        private const val BASE_URL_TWO = BuildConfig.BASE_URL_TWO

        private fun createOkHttpClient(token: String): OkHttpClient {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(requestHeaders)
            }
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
        }

        fun getApiServiceOne(token: String): ApiService {
            val client = createOkHttpClient(token)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_ONE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiServiceTwo(token: String): ApiService {
            val client = createOkHttpClient(token)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_TWO)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
