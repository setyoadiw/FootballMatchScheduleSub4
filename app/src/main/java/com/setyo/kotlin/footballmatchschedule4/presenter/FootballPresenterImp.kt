package com.setyo.kotlin.footballmatchschedule4.presenter

import android.util.Log
import com.google.gson.Gson
import com.setyo.kotlin.footballmatchschedule4.activity.MainView
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.EventsItem
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.ResponseEvents
import com.setyo.kotlin.footballmatchschedule4.network.ApiService
import com.setyo.kotlin.footballmatchschedule4.network.Network
import com.setyo.kotlin.footballmatchschedule4.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FootballPresenterImp(val MainView: MainView,
                           var gson: Gson,
                           var apiService: ApiService,
                           var context: CoroutineContextProvider) : FootballPresenter {

    init {
        this.context = CoroutineContextProvider()
    }


    override fun getDataLastEvents(event: String?) {

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiService
                    .doRequest(Network.getLastSchedule(event)).await(),
                    ResponseEvents::class.java
            )
//            MainView.showEventData(data.events?.get(0))
            MainView.berhasil(data.events as List<EventsItem>)

        }


    }

    override fun getDataNextEvents(event: String?) {

        GlobalScope.launch(context.main){
            val data2 = gson.fromJson(apiService
                    .doRequest(Network.getNextSchedule(event)).await(),
                    ResponseEvents::class.java
            )

            MainView.berhasilNextEvent(data2.events as List<EventsItem>)

        }

//        async(context?.main!!) {
//            val data2 = bg {
//                gson?.fromJson(apiService?.doRequest(Network.getNextSchedule(event)),
//                        ResponseEvents::class.java
//                )
//            }
//            MainView?.berhasilNextEvent(data2.await()?.events as List<EventsItem>)
//
//        }

    }


}