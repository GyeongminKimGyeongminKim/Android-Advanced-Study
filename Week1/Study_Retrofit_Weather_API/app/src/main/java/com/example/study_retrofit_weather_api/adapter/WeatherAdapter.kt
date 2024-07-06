package com.example.study_retrofit_weather_api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.study_retrofit_weather_api.R
import com.example.study_retrofit_weather_api.data.ModelWeather

class WeatherAdapter(private var items: List<ModelWeather> = emptyList()) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<ModelWeather>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: ModelWeather) {
            val imgWeather = itemView.findViewById<ImageView>(R.id.imgWeather)
            val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
            val tvHumidity = itemView.findViewById<TextView>(R.id.tvHumidity)
            val tvTemp = itemView.findViewById<TextView>(R.id.tvTemp)

            imgWeather.setImageResource(getRainImage(item.rainType, item.sky))
            tvTime.text = getTime(item.fcstTime)
            tvHumidity.text = "${item.humidity}%"
            tvTemp.text = "${item.temp}°"
        }

        private fun getRainImage(rainType: String, sky: String): Int {
            return when (rainType) {
                "0" -> getWeatherImage(sky)
                "1" -> R.drawable.rainy
                "2" -> R.drawable.hail
                "3" -> R.drawable.snowy
                "4" -> R.drawable.brash
                else -> getWeatherImage(sky)
            }
        }

        private fun getWeatherImage(sky: String): Int {
            return when (sky) {
                "1" -> R.drawable.sun
                "3" -> R.drawable.cloudy
                "4" -> R.drawable.blur
                else -> R.drawable.ic_launcher_foreground
            }
        }

        private fun getTime(fcstTime: String): String {
            if (fcstTime != "지금") {
                val hourSystem = fcstTime.toInt()
                val hourSystemString = hourSystem.toString()

                return when {
                    hourSystem == 0 -> "오전 12시"
                    hourSystem >= 2100 -> "오후 ${hourSystemString[0]}${hourSystemString[1]}시"
                    hourSystem == 1200 -> "오후 12시"
                    hourSystem > 1200 -> "오후 ${hourSystem - 1200}시"
                    hourSystem >= 1000 -> "오전 ${hourSystemString[0]}${hourSystemString[1]}시"
                    else -> "오전 ${hourSystemString[0]}시"
                }
            } else {
                return fcstTime
            }
        }
    }
}
