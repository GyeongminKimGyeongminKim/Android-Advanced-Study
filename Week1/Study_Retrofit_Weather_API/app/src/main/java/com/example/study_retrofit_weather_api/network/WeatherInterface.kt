package com.example.study_retrofit_weather_api.network

import com.example.study_retrofit_weather_api.BuildConfig
import com.example.study_retrofit_weather_api.data.WEATHER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 결과 xml 파일에 접근해서 정보 가져오기
interface WeatherInterface {
    // getUltraSrtFcst : 초단기 예보 조회 + 인증키
    @GET("getUltraSrtFcst?")
    fun getWeather(
        @Query("serviceKey") serviceKey: String,   // 인증키
        @Query("pageNo") page_no: Int,          // 페이지 번호
        @Query("numOfRows") num_of_rows: Int,   // 한 페이지 경과 수
        @Query("dataType") data_type: String,   // 응답 자료 형식
        @Query("base_date") base_date: Int,  // 발표 일자
        @Query("base_time") base_time: Int,  // 발표 시각
        @Query("nx") nx: Int,                // 예보지점 X 좌표
        @Query("ny") ny: Int,                 // 예보지점 Y 좌표
    ): Call<WEATHER>
}