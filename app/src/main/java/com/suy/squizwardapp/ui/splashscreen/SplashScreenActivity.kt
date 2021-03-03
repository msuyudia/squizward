package com.suy.squizwardapp.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.suy.squizwardapp.databinding.ActivitySplashScreenBinding
import com.suy.squizwardapp.ui.category.CategoryActivity
import splitties.activities.start

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            start<CategoryActivity>()
            finish()
        }, 3000)
    }
}