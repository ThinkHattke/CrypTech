package com.ubertech.cryptech.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ubertech.cryptech.R

class Login : AppCompatActivity() {

    // Views
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var register: TextView
    lateinit var login: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Views
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        register = findViewById(R.id.register)
        login = findViewById(R.id.imageView2)

        login.setOnClickListener {  }

        register.setOnClickListener { startActivity(Intent(this@Login, Register::class.java)) }

    }

}
