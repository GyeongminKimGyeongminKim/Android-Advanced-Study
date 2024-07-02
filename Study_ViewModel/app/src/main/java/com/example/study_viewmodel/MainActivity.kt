package com.example.study_viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.study_viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_1_holder, FragmentA())
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_2_holder, FragmentB())
                .commit()
        }
    }
}
