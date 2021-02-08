package com.example.pinalurisdavaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
      val LOADING_TIME: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()



        Handler().postDelayed({
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }, LOADING_TIME)
    }
}