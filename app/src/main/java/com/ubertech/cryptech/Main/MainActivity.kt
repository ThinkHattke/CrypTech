package com.ubertech.cryptech.Main

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.ubertech.cryptech.API.Services.ApiClient
import com.ubertech.cryptech.API.Services.ApiInterface
import com.ubertech.cryptech.R
import com.ubertech.cryptech.Utilities.TinyDB

class MainActivity : AppCompatActivity() {

    // Views
    private lateinit var hint: ImageView
    private lateinit var leaderboard: ImageView
    private lateinit var more: ImageView
    private lateinit var level: TextView
    private lateinit var image: ImageView
    private lateinit var success: LottieAnimationView
    private lateinit var loader: LottieAnimationView
    private lateinit var wrong: LottieAnimationView
    private lateinit var answer: EditText
    private lateinit var submit: TextView

    // Global data sources
    private var api: ApiInterface? = null
    private var db: TinyDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Views
        hint = findViewById(R.id.hint)
        leaderboard = findViewById(R.id.leaderboard)
        more = findViewById(R.id.more)
        level = findViewById(R.id.level)
        image = findViewById(R.id.image)
        success = findViewById(R.id.success)
        loader = findViewById(R.id.loader)
        wrong = findViewById(R.id.wrong)
        answer = findViewById(R.id.answer)
        submit = findViewById(R.id.submit)

        // Initiating tinyDB
        db = TinyDB(this@MainActivity)

        // Initiating Retrofit API
        api = ApiClient.client.create(ApiInterface::class.java)


    }
}
