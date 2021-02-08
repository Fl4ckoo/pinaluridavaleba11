package com.example.pinalurisdavaleba

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*





class LogInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()


        supportActionBar?.hide()
    }

    private fun init() {
        auth = Firebase.auth
        val btnSignIn = findViewById(R.id.btnSignIn2) as Button
        btnSignIn.setOnClickListener() {
            logIn()
        }


        val btnBack1 = findViewById(R.id.btnSignUp4) as Button
        btnBack1.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logIn() {
        val emailSignIn = eTextMail.text.toString()
        val passwordSignIn = eTextPassword.text.toString()
        if (emailSignIn.isNotEmpty() && passwordSignIn.isNotEmpty()) {

            auth.signInWithEmailAndPassword(emailSignIn, passwordSignIn)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            d("logIn", "signInWithEmail:success")
                            val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            d("logIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed. Try again!" + task.exception,
                                    Toast.LENGTH_LONG).show()
                        }
                    }


        }
    }
}