package com.setyo.kotlin.footballmatchschedule4.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setyo.kotlin.footballmatchschedule4.R
import com.setyo.kotlin.footballmatchschedule4.database.Favorite
import kotlinx.android.synthetic.main.recycler_favorite_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class FavoriteEventAdapter(private val favorite: List<Favorite>,private val context: Context ,
                           private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder((LayoutInflater.from(context).inflate(R.layout.recycler_favorite_item, parent, false)))

    }

    override fun getItemCount(): Int = favorite.size



    override fun onBindViewHolder(p0: FavoriteViewHolder, p1: Int) {
        p0.bindItem(favorite[p1], listener)

    }


}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {


        itemView.tvDate.text = SimpleDateFormat("EEE, d MMM yyyy")
                                .format(SimpleDateFormat("yyyy-MM-dd")
                                .parse(favorite.eventDate))
        itemView.tvHome.text = favorite.teamHome

        itemView.tvAway.text = favorite.teamAway

        if (favorite.scoreHome == "null" && favorite.scoreAway == "null"){
            itemView.tvSkor.text = " VS "
        }else{
            itemView.tvSkor.text = favorite.scoreHome+"VS"+favorite.scoreAway
        }


        itemView.onClick {
            listener(favorite)
        }
    }


}
