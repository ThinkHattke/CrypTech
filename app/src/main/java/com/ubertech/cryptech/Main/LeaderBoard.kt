package com.ubertech.cryptech.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ubertech.cryptech.R

class LeaderBoard : AppCompatActivity() {

    // Views
    private lateinit var back: ImageView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        back = findViewById(R.id.back)
        recyclerView = findViewById(R.id.recyclerView)

        back.setOnClickListener { onBackPressed() }

    }

    override fun onBackPressed() {
        finish()
    }

}
