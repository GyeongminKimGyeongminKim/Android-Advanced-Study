package com.example.study_retrofit_weather_api

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.study_retrofit_weather_api.adapter.WeatherAdapter
import com.example.study_retrofit_weather_api.component.Common
//import com.example.study_retrofit_weather_api.data.ITEM
import com.example.study_retrofit_weather_api.data.ModelWeather
import com.example.study_retrofit_weather_api.data.WEATHER
import com.example.study_retrofit_weather_api.databinding.ActivityMainBinding
import com.example.study_retrofit_weather_api.network.WeatherObject
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var baseDate = 20210510
    private var baseTime = 1400
    private var curPoint: Point? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherAdapter

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val permissionList = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissionList, 1)

        weatherAdapter = WeatherAdapter()
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.weatherRecyclerView.adapter = weatherAdapter

        binding.tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + " 날씨"

        requestLocation()

        binding.btnRefresh.setOnClickListener {
            requestLocation()
        }
    }

    private fun setWeather(nx: Int, ny: Int) {
        val cal = Calendar.getInstance()
        baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time).toInt()
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        val timeM = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time)

        baseTime = Common().getBaseTime(timeH, timeM).toInt()
        if (timeH == "00" && baseTime == 2330) {
            cal.add(Calendar.DATE, -1)
            baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time).toInt()
        }

        Log.d("API Request", "URL: http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=${BuildConfig.API_KEY}&numOfRows=60&pageNo=1&dataType=JSON&base_date=$baseDate&base_time=$baseTime&nx=$nx&ny=$ny")

        val call = WeatherObject.getRetrofitService().getWeather(
            BuildConfig.API_KEY,
            1,
            10,
            "JSON",
            baseDate,
            baseTime,
            nx,
            ny,
        )

        call.enqueue(object : Callback<WEATHER> {
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                Log.d("API Response", "Code: ${response.code()}, Raw: ${response.raw()}")

                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        Log.d("API Response Body", weatherResponse.toString())

                        val it: List<WEATHER.ITEM> = weatherResponse.response.body.items.item
                        val weatherArr = arrayOf(ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather())
                        var index = 0
                        val totalCount = weatherResponse.response.body.totalCount - 1
                        for (i in 0..totalCount) {
                            index %= 6
                            when (it[i].category) {
                                "PTY" -> weatherArr[index].rainType = it[i].fcstValue
                                "REH" -> weatherArr[index].humidity = it[i].fcstValue
                                "SKY" -> weatherArr[index].sky = it[i].fcstValue
                                "T1H" -> weatherArr[index].temp = it[i].fcstValue
                                else -> continue
                            }
                            index++
                        }

                        weatherArr[0].fcstTime = "지금"
                        for (i in 1..5) weatherArr[i].fcstTime = it[i].fcstTime

                        weatherAdapter.submitList(weatherArr.toList())
                        Toast.makeText(applicationContext, it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("api fail", "Response body is null")
                    }
                } else {
                    // 응답 본문을 문자열로 출력
                    val errorBody = response.errorBody()?.string()
                    Log.d("api fail", "Response failed: $errorBody")
                }
            }

            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                binding.tvError.text = "api fail : " + t.message.toString() + "\n 다시 시도해주세요."
                binding.tvError.visibility = View.VISIBLE
                Log.d("api fail", t.message.toString())
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000
            }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    p0.let {
                        for (location in it.locations) {
                            curPoint = Common().dfsXyConv(location.latitude, location.longitude)
                            binding.tvDate.text = SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + " 날씨"
                            setWeather(curPoint!!.x, curPoint!!.y)
                        }
                    }
                }
            }

            Looper.myLooper()?.let {
                locationClient.requestLocationUpdates(locationRequest, locationCallback, it)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
