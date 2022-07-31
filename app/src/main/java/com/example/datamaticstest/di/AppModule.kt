package com.example.datamaticstest.di

import com.example.datamaticstest.BuildConfig
import com.example.datamaticstest.service.ApiService
import com.example.datamaticstest.utils.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val baseURL = Constant.BASE_URL

    @Provides
    @Singleton
    internal fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            var request: Request? = null
            try {
                val authorizationToken = ""
                request = if (authorizationToken.isEmpty()) {
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .method(original.method, original.body).build()
                } else {
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", authorizationToken)
                        .method(original.method, original.body).build()
                }

            } catch (authFailureError: Exception) {
                authFailureError.printStackTrace()
            }
            chain.proceed(request!!)
        }
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {

         val interceptor = HttpLoggingInterceptor()
         interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.interceptors().add(interceptor)

        okHttpBuilder.readTimeout(Constant.READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(Constant.CONNECTION_TIME_OUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            //okHttpBuilder.addInterceptor(OkHttpProfilerInterceptor())
            okHttpBuilder.addInterceptor(interceptor)

        }
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun getRetrofitInstance(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}