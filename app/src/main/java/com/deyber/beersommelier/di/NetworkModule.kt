package com.deyber.beersommelier.di

import android.content.Context
import com.deyber.beersommelier.data.network.BeerClient
import com.deyber.beersommelier.utils.constants.RetrofitConstants.baseUrl
import com.deyber.beersommelier.utils.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {

    @Singleton
    @Provides
    fun provideBeerClient(retrofit: Retrofit):BeerClient = retrofit.create(BeerClient::class.java)


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: NetworkInterceptor):OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideInternetInterceptor(@ApplicationContext context:Context):NetworkInterceptor = NetworkInterceptor(context)


}