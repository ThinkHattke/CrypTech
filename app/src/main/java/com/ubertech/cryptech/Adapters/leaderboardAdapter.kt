package com.ubertech.cryptech.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ubertech.cryptech.API.Models.Response.leaderboardUser
import com.ubertech.cryptech.R
import java.util.ArrayList

class leaderboardAdapter (context: Context?) : RecyclerView.Adapter<leaderboardAdapter.MyViewHolder>() {


    //Global Data
    private var users: List<leaderboardUser> = ArrayList()
    private var inflater: LayoutInflater? = null
    lateinit var view: View
    lateinit var holder: MyViewHolder
    private var context: Context? = null

    init {

        if (context != null) {

            this.context = context
            inflater = LayoutInflater.from(context)

        }
    }

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        view = inflater!!.inflate(R.layout.card_leaderboard, parent, false)
        holder = MyViewHolder(view)
        return holder
    }


    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //Fetching Data
        val user = users[position]

        holder.position!!.text = user.level.toString()
        holder.name!!.text = user.full_name

    }

    //Setting the arraylist
    fun setListContent(users: List<leaderboardUser>) {

        this.users = users
        notifyItemRangeChanged(0, users.size)

    }


    //Setting the count
    override fun getItemCount(): Int {
        return users.size
    }



    //View holder class, where all view components are defined
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        //UI  Components
        var position: TextView? = null
        var name: TextView? = null

        init {

            itemView.setOnClickListener(this)

            //Setting Views
            position = itemView.findViewById(R.id.position)
            name = itemView.findViewById(R.id.name)

        }

        override fun onClick(v: View) {

        }


    }

}