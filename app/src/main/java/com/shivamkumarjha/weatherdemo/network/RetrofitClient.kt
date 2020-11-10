package com.shivamkumarjha.weatherdemo.network

import android.content.Context
import android.net.ConnectivityManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shivamkumarjha.weatherdemo.BuildConfig
import com.shivamkumarjha.weatherdemo.config.Constants
import com.shivamkumarjha.weatherdemo.ui.BaseApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Module
class RetrofitClient {
    @Inject
    lateinit var mContext: Context

    init {
        BaseApplication.baseApplicationComponent.inject(this)
    }

    @Singleton
    @Provides
    fun getHttpCache(): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(mContext.cacheDir, cacheSize.toLong())
    }

    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder().setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    @Singleton
    @Provides
    fun getOkHTTPClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // set your desired log level
        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        client.connectTimeout(5, TimeUnit.MINUTES)
        client.readTimeout(5, TimeUnit.MINUTES)
        // add logging as last interceptor
        client.addInterceptor(logging)
        client.addInterceptor(HttpInterceptor(connectivityManager))
        client.cache(cache)
        client.addNetworkInterceptor(StethoInterceptor())
        client.retryOnConnectionFailure(true)
        client.connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        return client.build()
    }

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .baseUrl(Constants.BASE_URL)
            .client(getOkHTTPClient(getHttpCache()))
            .build()
    }

    @Singleton
    @Provides
    fun getApiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }
}
