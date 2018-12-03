package com.setyo.kotlin.footballmatchschedule4.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(val id: Long?, val eventId: String?, val eventDate: String?,
                    val eventTime: String?, val idHome: String?, val teamHome: String?,
                    val scoreHome: String?, val idAway: String?, val teamAway: String?,
                    val scoreAway: String?, val gkHome : String?, val gkAway : String?,
                    val dfHome : String?, val dfAway : String?, val mfHome : String?,
                    val mfAway : String?, val fwHome : String?, val fwAway : String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITES: String = "TABLE_FAVORITES"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_TIME: String = "EVENT_TIME"
        const val ID_HOME: String = "ID_HOME"
        const val TEAM_HOME: String = "TEAM_HOME"
        const val SCORE_HOME: String = "SCORE_HOME"
        const val ID_AWAY: String = "ID_AWAY"
        const val TEAM_AWAY: String = "TEAM_AWAY"
        const val SCORE_AWAY: String = "SCORE_AWAY"
        const val GK_HOME : String = "GK_HOME"
        const val GK_AWAY : String = "GK_AWAY"
        const val DF_HOME : String = "DF_HOME"
        const val DF_AWAY : String = "DF_AWAY"
        const val MF_HOME : String = "MF_HOME"
        const val MF_AWAY : String = "MF_AWAY"
        const val FW_HOME : String = "FW_HOME"
        const val FW_AWAY : String = "FW_AWAY"

    }
}