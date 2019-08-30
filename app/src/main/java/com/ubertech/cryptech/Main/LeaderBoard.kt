package com.ubertech.cryptech.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ubertech.cryptech.API.Models.Response.LeaderboardResponse
import com.ubertech.cryptech.API.Models.Response.leaderboardUser
import com.ubertech.cryptech.API.Services.ApiClient
import com.ubertech.cryptech.API.Services.ApiInterface
import com.ubertech.cryptech.Adapters.leaderboardAdapter
import com.ubertech.cryptech.R
import com.ubertech.cryptech.Utilities.TinyDB
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class LeaderBoard : AppCompatActivity() {

    // Views
    private lateinit var back: ImageView
    private lateinit var recyclerView: RecyclerView

    // Global data sources
    private var api: ApiInterface? = null
    private var db: TinyDB? = null

    // Local data
    private val users = arrayListOf<leaderboardUser>()
    var adapter: leaderboardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        // Initiating tinyDB
        db = TinyDB(this@LeaderBoard)

        // Initiating Retrofit API
        api = ApiClient.client.create(ApiInterface::class.java)

        back = findViewById(R.id.back)
        recyclerView = findViewById(R.id.recyclerView)

        back.setOnClickListener { onBackPressed() }

        api!!.requestLeaderBoard(db!!.getString("jwt")).enqueue(object :retrofit2.Callback<List<leaderboardUser>> {
            override fun onFailure(call: Call<List<leaderboardUser>>, t: Throwable) {
                Timber.e(t)
                Toast.makeText(this@LeaderBoard, "Something Wen't wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<leaderboardUser>>, response: Response<List<leaderboardUser>>) {

                if(response.isSuccessful) {

                    val layoutManager = LinearLayoutManager(this@LeaderBoard)
                    users.addAll(response.body()!!)
                    adapter!!.setListContent(users)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter

                } else {

                    Toast.makeText(this@LeaderBoard, "Try again later", Toast.LENGTH_SHORT).show()

                }

            }


        })

    }

    override fun onBackPressed() {
        finish()
    }

}
