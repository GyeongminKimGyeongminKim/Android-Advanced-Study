package com.example.study_module.di

import com.example.data.service.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun providesUnsplashService(retrofitClient: Retrofit) : UnsplashService =
        retrofitClient.create(UnsplashService::class.java)
}