package com.ubertech.cryptech.Main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.squareup.picasso.Picasso
import com.ubertech.cryptech.API.Models.Request.SubmitRequest
import com.ubertech.cryptech.API.Models.Response.LevelResponse
import com.ubertech.cryptech.API.Models.Response.VerifyResponse
import com.ubertech.cryptech.API.Services.ApiClient
import com.ubertech.cryptech.API.Services.ApiInterface
import com.ubertech.cryptech.Auth.Login
import com.ubertech.cryptech.Auth.Register
import com.ubertech.cryptech.R
import com.ubertech.cryptech.Utilities.TinyDB
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


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
    private lateinit var submitLayout: LinearLayout

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
        submitLayout = findViewById(R.id.submitLayout)
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

            loader.visibility = View.VISIBLE
            hint.isClickable = false
            submit.isClickable = false

            if(answer.text.isNullOrEmpty()) {

                Toast.makeText(this@MainActivity, "Answer cannot be Empty!", Toast.LENGTH_SHORT).show()
                submit.isClickable = true

            } else {

                loader.visibility = View.VISIBLE
                image.visibility = View.INVISIBLE

                api!!.submitAnswer(db!!.getString("jwt"),SubmitRequest(answer.text.toString()))
                        .enqueue(object : retrofit2.Callback<VerifyResponse>{
                            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {

                                submit.isClickable = true
                                Timber.e(t)
                                submit.text = ""
                                loader.visibility = View.GONE
                                Toast.makeText(this@MainActivity, "Something Wen't wrong", Toast.LENGTH_SHORT).show()

                            }

                            override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {
                                submit.isClickable = true
                                when {
                                    response.isSuccessful -> {
                                        answer.text.clear()
                                        fetchData()
                                    }

                                    response.code() == 401 -> {

                                        loader.visibility = View.GONE
                                        image.visibility = View.VISIBLE
                                        Toast.makeText(this@MainActivity, "Incorrect Answer", Toast.LENGTH_SHORT).show()

                                    }
                                    else -> {

                                        loader.visibility = View.GONE
                                        image.visibility = View.VISIBLE
                                        Toast.makeText(this@MainActivity, "Something Wen't wrong", Toast.LENGTH_SHORT).show()

                                    }
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

            startActivity(Intent(this@MainActivity, LeaderBoard::class.java))

        }

        more.setOnClickListener {

            val popMenu = PopupMenu(this@MainActivity, more)
            popMenu.menuInflater.inflate(R.menu.options, popMenu.menu)

            popMenu.setOnMenuItemClickListener {item ->

                if(item.itemId == R.id.logout) {

                    db!!.putBoolean("logged",false)
                    db!!.putBoolean("verified",false)
                    startActivity(Intent(this@MainActivity, Login::class.java))
                    finish()

                }

                if(item.itemId == R.id.rules) {



                }

                if(item.itemId == R.id.about) {



                }

                if(item.itemId == R.id.reload) {

                    fetchData()

                }

                false

            }

            popMenu.show()

        }

        fetchData()

    }

    private fun fetchData() {

        loader.visibility = View.VISIBLE
        image.visibility = View.INVISIBLE

        api!!.requestLevel(db!!.getString("jwt")).enqueue(object : retrofit2.Callback<LevelResponse> {

            override fun onFailure(call: Call<LevelResponse>, t: Throwable) {
                loader.visibility = View.GONE
                image.visibility = View.VISIBLE
                Timber.e(t)
                Toast.makeText(this@MainActivity, "Something Wen't wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LevelResponse>, response: Response<LevelResponse>) {

                when {
                    response.isSuccessful -> {

                        db!!.putString("url", response.body()!!.image_url!!)
                        db!!.putString("hint", response.body()!!.hint!!)
                        db!!.putString("level", response.body()!!.level!!.toString())
                        setData()

                    }
                    response.code() == 400 -> {

                        startActivity(Intent(this@MainActivity, Login::class.java))
                        finish()

                    }
                    else -> Toast.makeText(this@MainActivity, "Something wen't wrong, Try again later", Toast.LENGTH_SHORT).show()
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
        Picasso.get().load(url).error(R.drawable.error).into(object : com.squareup.picasso.Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                image.visibility = View.VISIBLE
                submitLayout.visibility = View.GONE
                loader.visibility = View.GONE
                Log.wtf("Erroe is", e!!.message)
                image.setImageResource(R.drawable.error)
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                image.visibility = View.VISIBLE
                submitLayout.visibility = View.VISIBLE
                image.setImageBitmap(bitmap)
                loader.visibility = View.GONE

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })

    }

}
