package com.ubertech.cryptech.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ubertech.cryptech.R

class Register : AppCompatActivity() {

    // Views
    lateinit var back: ImageView
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var name: EditText
    lateinit var mobile: EditText
    lateinit var college: EditText
    lateinit var year: EditText
    lateinit var section: EditText
    lateinit var reg: EditText
    lateinit var submit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Views
        back = findViewById(R.id.back)
        email = findViewById(R.id.email)
        name = findViewById(R.id.name)
        mobile = findViewById(R.id.mobile)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        college = findViewById(R.id.college)
        year = findViewById(R.id.year)
        section = findViewById(R.id.section)
        reg = findViewById(R.id.reg)
        submit = findViewById(R.id.submit)

        back.setOnClickListener { onBackPressed() }

    }

    override fun onBackPressed() {
        finish()
    }

}
