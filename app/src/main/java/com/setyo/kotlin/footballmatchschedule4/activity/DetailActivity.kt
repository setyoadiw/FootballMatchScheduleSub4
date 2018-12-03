package com.setyo.kotlin.footballmatchschedule4.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.setyo.kotlin.footballmatchschedule4.model.TeamDetail.TeamsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.anko.ctx
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.setyo.kotlin.footballmatchschedule4.R
import com.setyo.kotlin.footballmatchschedule4.database.Favorite
import com.setyo.kotlin.footballmatchschedule4.database.database
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.EventsItem
import com.setyo.kotlin.footballmatchschedule4.model.TeamDetail.ResponseTeamDetail
import com.setyo.kotlin.footballmatchschedule4.network.ApiService
import com.setyo.kotlin.footballmatchschedule4.network.Network
import com.setyo.kotlin.footballmatchschedule4.presenter.TeamPresenterImp
import com.setyo.kotlin.footballmatchschedule4.util.CoroutineContextProvider
import kotlinx.coroutines.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


class DetailActivity : AppCompatActivity() ,DetailView{

    lateinit var presenter : TeamPresenterImp
    val api = ApiService()
    val gson = Gson()
    var context: CoroutineContextProvider? = CoroutineContextProvider()

//    lateinit var dataTeamDetail : java.util.ArrayList<TeamsItem>
    private var isFavorite: Boolean = false
    lateinit var idHome : String
    lateinit var idAway : String
    private var idEvent : String = ""
    lateinit var dateEvent : String
    lateinit var timeEvent : String
    lateinit var homeTeam : String
    lateinit var homeScore : String
    lateinit var awayTeam : String
    lateinit var awayScore : String

    var gkHomeStr : String = ""
    var gkAwayStr : String = ""
    var dfHomeStr : String = ""
    var dfAwayStr : String = ""
    var mfHomeStr : String = ""
    var mfAwayStr : String = ""
    var fwHomeStr : String = ""
    var fwAwayStr : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)


        getDataAndInitiate()
        initPresenter()
        initView(idHome,idAway)
        castToView()


        fab.setOnClickListener { view ->

            if (isFavorite) removeFromFavorite() else addToFavorite()
            isFavorite = !isFavorite
            setFavorite()
        }

        favoriteState()
        setFavorite()

    }


    override fun getTeamHome(data: List<TeamsItem>) {

        Picasso.with(ctx).load(data.get(0).strTeamBadge).into(imgHome)

        Picasso.with(ctx).load(data.get(0).strStadiumThumb).into(img_header)

    }

    override fun getTeamAway(data: List<TeamsItem>) {

        Picasso.with(ctx).load(data.get(0).strTeamBadge).into(imgAway)

    }


    private fun getDataAndInitiate() {

        val dataParcel = intent.extras.getParcelable<EventsItem>("dataParcel")

        val dataParcelFav = intent.extras.getParcelable<Favorite>("dataParcelFav")


        if (dataParcel != null) {

            idHome = dataParcel.idHomeTeam.toString()
            idAway = dataParcel.idAwayTeam.toString()
            idEvent = dataParcel.idEvent.toString()
            dateEvent = dataParcel.dateEvent.toString()
            timeEvent = dataParcel.strTime.toString()
            homeTeam = dataParcel.strHomeTeam.toString()
            homeScore = dataParcel.intHomeScore.toString()
            awayTeam = dataParcel.strAwayTeam.toString()
            awayScore = dataParcel.intAwayScore.toString()

            gkHomeStr = dataParcel.strHomeLineupGoalkeeper.toString()
            gkAwayStr = dataParcel.strAwayLineupGoalkeeper.toString()
            dfHomeStr = dataParcel.strHomeLineupDefense.toString()
            dfAwayStr = dataParcel.strAwayLineupDefense.toString()
            mfHomeStr = dataParcel.strHomeLineupMidfield.toString()
            mfAwayStr = dataParcel.strAwayLineupMidfield.toString()
            fwHomeStr = dataParcel.strHomeLineupForward.toString()
            fwAwayStr = dataParcel.strAwayLineupForward.toString()


        }else if (dataParcelFav != null) {

            idHome = dataParcelFav.idHome.toString()
            idAway = dataParcelFav.idAway.toString()
            idEvent = dataParcelFav.eventId.toString()
            dateEvent = dataParcelFav.eventDate.toString()
            timeEvent = dataParcelFav.eventTime.toString()
            homeTeam = dataParcelFav.teamHome.toString()
            homeScore = dataParcelFav.scoreHome.toString()
            awayTeam = dataParcelFav.teamAway.toString()
            awayScore = dataParcelFav.scoreAway.toString()

            gkHomeStr = dataParcelFav.gkHome.toString()
            gkAwayStr = dataParcelFav.gkAway.toString()
            dfHomeStr = dataParcelFav.dfHome.toString()
            dfAwayStr = dataParcelFav.dfAway.toString()
            mfHomeStr = dataParcelFav.mfHome.toString()
            mfAwayStr = dataParcelFav.mfAway.toString()
            fwHomeStr = dataParcelFav.fwHome.toString()
            fwAwayStr = dataParcelFav.fwAway.toString()

        }

    }

    private fun initPresenter() {
        presenter = TeamPresenterImp(this,gson,api, this!!.context!!)

    }

    private fun initView(idHome: String,idAway: String) {

        val collapsingToolbar = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        collapsingToolbar.title = "Match Detail"
        presenter.getTeamDetail(idHome,idAway)



    }

    private fun castToView() {

        tvHomeTeam.text = homeTeam
        tvAwayTeam.text = awayTeam
        tvHomeScore.text = homeScore
        tvAwayScore.text = awayScore
        tvDate.text = dateEvent

        if (gkHomeStr == "null" && dfHomeStr == "null"){

            cvGk.visibility = View.INVISIBLE
            cvDf.visibility = View.INVISIBLE
            cvMf.visibility = View.INVISIBLE
            cvFw.visibility = View.INVISIBLE
            tvHomeScore.text = ""
            tvAwayScore.text = ""

        }else{

            gkHome.text = explode(gkHomeStr)
            gkAway.text = explode(gkAwayStr)
            dfHome.text = explode(dfHomeStr)
            dfAway.text = explode(dfAwayStr)
            mfHome.text = explode(mfHomeStr)
            mfAway.text = explode(mfAwayStr)
            fwHome.text = explode(fwHomeStr)
            fwAway.text = explode(fwAwayStr)


        }
    }

    private fun explode(pemain: String?): String? {
        return pemain?.replace("; ", "\n")
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITES)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEvent)
            val favorite = result.parseList(classParser<Favorite>())
            Log.d("favoriteState", favorite.toString())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITES,
                        Favorite.EVENT_ID to idEvent,
                        Favorite.EVENT_DATE to dateEvent,
                        Favorite.EVENT_TIME to timeEvent,
                        Favorite.ID_HOME to idHome,
                        Favorite.TEAM_HOME to homeTeam,
                        Favorite.SCORE_HOME to homeScore,
                        Favorite.ID_AWAY to idAway,
                        Favorite.TEAM_AWAY to awayTeam,
                        Favorite.SCORE_AWAY to awayScore,
                        Favorite.GK_HOME to gkHomeStr,
                        Favorite.GK_AWAY to gkAwayStr,
                        Favorite.DF_HOME to dfHomeStr,
                        Favorite.DF_AWAY to dfAwayStr,
                        Favorite.MF_HOME to mfHomeStr,
                        Favorite.MF_AWAY to mfAwayStr,
                        Favorite.FW_HOME to fwHomeStr,
                        Favorite.FW_AWAY to fwAwayStr
                        )
                }

            Snackbar.make(app_bar, "This Match saved to Favorited list", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        } catch (e: SQLiteConstraintException){

        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITES, "(EVENT_ID = {id})",
                        "id" to idEvent)
            }

        Snackbar.make(app_bar, "This Match removed to Favorited list", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        } catch (e: SQLiteConstraintException){

        Snackbar.make(app_bar, e.localizedMessage, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }
    }

    private fun setFavorite() {
        if (isFavorite){
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_favorite))
        }

        else{
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_unfavorite))
        }

    }
}
