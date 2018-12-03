package com.setyo.kotlin.footballmatchschedule4.presenter

import com.google.gson.Gson
import com.setyo.kotlin.footballmatchschedule4.activity.MainView
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.EventsItem
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.ResponseEvents
import com.setyo.kotlin.footballmatchschedule4.network.ApiService
import com.setyo.kotlin.footballmatchschedule4.network.Network
import com.setyo.kotlin.footballmatchschedule4.util.TestCoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FootballPresenterImpTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var service: ApiService

    private lateinit var presenter: FootballPresenterImp

    @Mock
    private
    lateinit var gson: Gson

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = FootballPresenterImp(view,gson,service, TestCoroutineContextProvider())

    }

    @Test
    fun getDataLastEvents(){

//        val event: ArrayList<EventsItem> = arrayListOf()
//        val response : ResponseEvents = mock()
//        val response = mock(ResponseEvents::class.java
        val event: MutableList<EventsItem> = mutableListOf()
        val response = ResponseEvents(event)
        val idLeague = "4328"


        GlobalScope.launch {
            Mockito.`when`(gson.fromJson(service
                    .doRequest(Network.getLastSchedule(idLeague)).await(),
                    ResponseEvents::class.java
            )).thenReturn(response)


            presenter.getDataLastEvents(idLeague)
            Mockito.verify(view).berhasil(event)

        }



    }


}