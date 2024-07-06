package com.example.study_retrofit_weather_api.network

import com.example.study_retrofit_weather_api.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object WeatherObject {

    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Json 데이터를 사용자가 정의한 Java 객채로 변환해주는 라이브러리
            .build()
    }
    fun getRetrofitService(): WeatherInterface {
        return getRetrofit().create(WeatherInterface::class.java) //retrofit 객체 만듦!
    }
}