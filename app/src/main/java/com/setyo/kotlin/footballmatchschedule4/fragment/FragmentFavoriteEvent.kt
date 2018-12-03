package com.setyo.kotlin.footballmatchschedule4.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.setyo.kotlin.footballmatchschedule4.R
import com.setyo.kotlin.footballmatchschedule4.activity.DetailActivity
import com.setyo.kotlin.footballmatchschedule4.adapter.FavoriteEventAdapter
import com.setyo.kotlin.footballmatchschedule4.database.Favorite
import com.setyo.kotlin.footballmatchschedule4.database.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh


class FragmentFavoriteEvent : Fragment()  {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragView = inflater.inflate(R.layout.fragment_favorite_event, container, false)
        swipeRefresh = fragView.find(R.id.swipe_refresh_favorite)
        listEvent = fragView.find(R.id.recyclerviewFavorit)


        adapter = FavoriteEventAdapter(favorites,requireContext()){

            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("dataParcelFav",it)
            startActivity(intent)
        }
        listEvent.layoutManager = LinearLayoutManager(requireContext())

        listEvent.adapter = adapter
        showFavorite()


        swipeRefresh.onRefresh {
            showFavorite()
        }


        return fragView
    }



    override fun onResume() {
        super.onResume()
        showFavorite()

    }

    private fun showFavorite(){
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITES)
            val favorite = result.parseList(classParser<Favorite>())
            Log.d("adapterTestdataFavorite", favorite.toString())

            favorites.addAll(favorite)
            listEvent.adapter = adapter

            adapter.notifyDataSetChanged()
        }
    }


    companion object {
        fun newInstance(): FragmentFavoriteEvent {
            val fragment = FragmentFavoriteEvent()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
