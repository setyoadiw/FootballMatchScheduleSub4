package com.setyo.kotlin.footballmatchschedule4.activity

import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.EventsItem

interface MainView {

    fun berhasil(data: List<EventsItem>)
    fun berhasilNextEvent(data: List<EventsItem>)
    fun gagal(pesan : String)

}