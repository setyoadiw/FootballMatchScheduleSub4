package com.setyo.kotlin.footballmatchschedule4.activity

import com.setyo.kotlin.footballmatchschedule4.model.TeamDetail.TeamsItem

interface DetailView {

    fun getTeamHome(data: List<TeamsItem>)
    fun getTeamAway(data: List<TeamsItem>)


}