package com.example.pinalurisdavaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        init()
           

        supportActionBar?.hide()
    }
    private fun init(){

        val btnSignIn1 =findViewById(R.id.btnSignIn1) as Button
        btnSignIn1.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
        val btnSignUp2 = findViewById(R.id.btnSignUp3) as Button
        btnSignUp2.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}