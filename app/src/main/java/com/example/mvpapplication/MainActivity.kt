package com.example.mvpapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvpapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileInfo.apply {
            topText = "Selamat Pagi,"
            bottomText = "Viola Maulana"
            setTextSize(topTextSize = 12f, bottomTextSize = 24f)
            setTextColor(topColorId = R.color.white, bottomColorId = R.color.white)
            setFontFamily(
                topFontFamily = R.font.roboto_regular,
                bottomFontFamily = R.font.roboto_medium
            )
        }

        binding.cardProfile.apply {
            setTextSize(topTextSize = 12f, bottomTextSize = 24f)
        }
    }
}