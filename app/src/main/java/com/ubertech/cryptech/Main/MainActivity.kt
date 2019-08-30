package com.ubertech.cryptech.Main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.squareup.picasso.Picasso
import com.ubertech.cryptech.API.Models.Request.SubmitRequest
import com.ubertech.cryptech.API.Models.Response.LevelResponse
import com.ubertech.cryptech.API.Models.Response.VerifyResponse
import com.ubertech.cryptech.API.Services.ApiClient
import com.ubertech.cryptech.API.Services.ApiInterface
import com.ubertech.cryptech.Auth.Register
import com.ubertech.cryptech.R
import com.ubertech.cryptech.Utilities.TinyDB
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*


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

        hint.isClickable = false
        submit.isClickable = false

        // Initiating tinyDB
        db = TinyDB(this@MainActivity)

        // Initiating Retrofit API
        api = ApiClient.client.create(ApiInterface::class.java)

        submit.setOnClickListener {

            hint.isClickable = false
            submit.isClickable = false

            if(answer.text.isNullOrEmpty()) {

                Toast.makeText(this@MainActivity, "Answer cannot be Empty!", Toast.LENGTH_SHORT).show()
                submit.isClickable = true

            } else {

                api!!.submitAnswer(db!!.getString("jwt"),SubmitRequest(answer.text.toString()))
                        .enqueue(object : retrofit2.Callback<VerifyResponse>{
                            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {

                                submit.isClickable = true
                                Timber.e(t)
                                Toast.makeText(this@MainActivity, "Something Wen't wrong", Toast.LENGTH_SHORT).show()

                            }

                            override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {

                                if(response.isSuccessful) {

                                    fetchData()

                                }

                            }


                        })

            }

        }

        hint.setOnClickListener {

            // Setting up the dialog box
            val dialog =  Dialog(this@MainActivity)
            dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_hint)

            // Declaring all views
            val value: TextView
            val dismiss: CircleImageView

            // Views
            value = dialog.findViewById(R.id.value)
            dismiss = dialog.findViewById(R.id.dismiss)

            value.text = db!!.getString("hint")

            dismiss.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

        leaderboard.setOnClickListener {

            startActivity(Intent(this@MainActivity, Register::class.java))

        }

    }

    private fun fetchData() {

        api!!.requestLevel(db!!.getString("jwt")).enqueue(object : retrofit2.Callback<LevelResponse> {

            override fun onFailure(call: Call<LevelResponse>, t: Throwable) {
                Timber.e(t)
                Toast.makeText(this@MainActivity, "Something Wen't wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LevelResponse>, response: Response<LevelResponse>) {

                if (response.isSuccessful) {

                    db!!.putString("url", response.body()!!.image_url!!)
                    db!!.putString("hint", response.body()!!.hint!!)
                    db!!.putString("level", response.body()!!.level!!.toString())
                    setData()

                }

            }


        })

    }

    @SuppressLint("SetTextI18n")
    private fun setData() {

        val url = db!!.getString("url")
        val Level = db!!.getString("level")

        hint.isClickable = true
        submit.isClickable = true
        level.text = "Level $Level"
        Picasso.get().load(url).into(image)



    }

}
