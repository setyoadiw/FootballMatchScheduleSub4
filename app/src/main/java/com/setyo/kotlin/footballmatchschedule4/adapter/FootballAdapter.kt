package com.setyo.kotlin.footballmatchschedule4.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setyo.kotlin.footballmatchschedule4.R
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.EventsItem
import kotlinx.android.synthetic.main.recycler_event_item.view.*
import java.util.ArrayList

class FootballAdapter(mContext: Context, data: List<EventsItem>, val listener: (EventsItem) -> Unit) : RecyclerView.Adapter<FootballAdapter.MyHolder>() {

    var eventData : List<EventsItem>
    var mcontext : Context

    init {
        eventData = data
        this.mcontext = mContext
    }

    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        override fun onClick(v: View?) {
            Log.d("tesclick","masuk")

        }

        fun bind(get: EventsItem, listener: (EventsItem) -> Unit, mContext: Context) {

            itemView.tvteam1.text = get.strHomeTeam
            itemView.tvscore1.text = get.intHomeScore
            itemView.tvteam2.text = get.strAwayTeam
            itemView.tvscore2.text = get.intAwayScore
            itemView.tvDate.text = get.dateEvent

            itemView.setOnClickListener {
                listener(get)
            }
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        var view = LayoutInflater.from(p0?.context).inflate(R.layout.recycler_event_item,p0,false)

        return MyHolder(view)

    }

    override fun getItemCount(): Int {
        return eventData.count()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder?.bind(eventData.get(position),listener,mcontext)


    }



}