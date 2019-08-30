package com.ubertech.cryptech.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ubertech.cryptech.API.Models.Request.RegistrationRequest
import com.ubertech.cryptech.API.Models.Response.RegistrationResponse
import com.ubertech.cryptech.API.Services.ApiClient
import com.ubertech.cryptech.API.Services.ApiInterface
import com.ubertech.cryptech.Main.MainActivity
import com.ubertech.cryptech.R
import com.ubertech.cryptech.Utilities.TinyDB
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

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

    // Global data sources
    private var api: ApiInterface? = null
    private var db: TinyDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Views
        back = findViewById(R.id.back)
        email = findViewById(R.id.email)
        name = findViewById(R.id.name)
        mobile = findViewById(R.id.mobile)
        password = findViewById(R.id.password)
        college = findViewById(R.id.college)
        year = findViewById(R.id.year)
        section = findViewById(R.id.section)
        reg = findViewById(R.id.reg)
        submit = findViewById(R.id.submit)

        // Initiating tinyDB
        db = TinyDB(this@Register)

        // Initiating Retrofit API
        api = ApiClient.client.create(ApiInterface::class.java)

        back.setOnClickListener { onBackPressed() }

        submit.setOnClickListener {

            when {
                email.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter Email ID to continue", Toast.LENGTH_SHORT).show()
                name.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your Name to continue", Toast.LENGTH_SHORT).show()
                mobile.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your Mobile Number to continue", Toast.LENGTH_SHORT).show()
                password.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your Password to continue", Toast.LENGTH_SHORT).show()
                college.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your College Name to continue", Toast.LENGTH_SHORT).show()
                year.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your Year to continue", Toast.LENGTH_SHORT).show()
                section.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your Section to continue", Toast.LENGTH_SHORT).show()
                reg.text.isNullOrEmpty() -> Toast.makeText(this@Register, "Enter your Registration Number to continue", Toast.LENGTH_SHORT).show()
                else -> api!!.requestRegister(RegistrationRequest(email.text.toString(),name.text.toString(), mobile.text.toString(),
                        password.text.toString(), college.text.toString(),year.text.toString(), section.text.toString(),
                        reg.text.toString())).enqueue(object : retrofit2.Callback<RegistrationResponse>{
                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        Timber.e(t)
                        Toast.makeText(this@Register, "Something Wen't wrong", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {

                        if(response.isSuccessful) {

                            db!!.putString("jwt", response.body()!!.jwt!!)
                            db!!.putBoolean("logged",true)

                            startActivity(Intent(this@Register, Verify::class.java))
                            finish()


                        } else {

                            Toast.makeText(this@Register, "Invalid data", Toast.LENGTH_SHORT).show()


                        }

                    }


                })
            }

        }

    }

    override fun onBackPressed() {
        finish()
    }

}
