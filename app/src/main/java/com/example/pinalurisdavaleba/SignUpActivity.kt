package com.example.pinalurisdavaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()

        supportActionBar?.hide()
    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        btnSignUp3.setOnClickListener {
            if (eTextMail2.text.toString().isNotEmpty() || eTextPassword2.text.toString().isNotEmpty() || eTextRepassword.text.toString().isEmpty()) {
                Toast.makeText(this, "SignUp is Success!", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Email format is not Correct", Toast.LENGTH_LONG).show()
            }
            signUp()


            val btnBack2 = findViewById(R.id.btnSignIn3) as Button
            btnBack2.setOnClickListener {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }


        }

    }


    private fun signUp() {
        val email: String = eTextMail2.text.toString()
        val password: String = eTextPassword2.text.toString()
        val confirmPassword: String = eTextRepassword.text.toString()


        if (email.isNotEmpty() &&
                password.isNotEmpty() && confirmPassword.isNotEmpty()
        ) {
            if (password == confirmPassword) {


                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->

                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                d("signUp", "createUserWithEmail:success")
                                val user = auth.currentUser

                            } else {
                                // If sign in fails, display a message to the user.
                                d("signUp", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed. Check your information and try again!",
                                        Toast.LENGTH_SHORT).show()
                            }
                        }

            }

        }

    }



}