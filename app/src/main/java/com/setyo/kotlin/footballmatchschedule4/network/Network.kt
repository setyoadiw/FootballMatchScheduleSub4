package com.setyo.kotlin.footballmatchschedule4.network

import com.setyo.kotlin.footballmatchschedule4.BuildConfig

object Network {

        fun getLastSchedule(league: String?): String{
                return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id="+league
        }

        fun getNextSchedule(league: String?): String{
                return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id="+league
        }

        fun getTeamDetail(teamId: String?): String{
                return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id="+teamId
        }

}