package com.setyo.kotlin.footballmatchschedule4.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.setyo.kotlin.footballmatchschedule4.adapter.FootballAdapter
import com.setyo.kotlin.footballmatchschedule4.R
import com.setyo.kotlin.footballmatchschedule4.activity.DetailActivity
import com.setyo.kotlin.footballmatchschedule4.activity.MainView
import com.setyo.kotlin.footballmatchschedule4.model.EventSchedule.EventsItem
import com.setyo.kotlin.footballmatchschedule4.network.ApiService
import com.setyo.kotlin.footballmatchschedule4.presenter.FootballPresenterImp
import com.setyo.kotlin.footballmatchschedule4.util.CoroutineContextProvider
import kotlinx.android.synthetic.main.fragment_next_event.*


class FragmentNextEvent : Fragment() , MainView {


    lateinit var presenter : FootballPresenterImp
    val api = ApiService()
    val gson = Gson()
    var context: CoroutineContextProvider? = CoroutineContextProvider()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        initPresenter()
        initView()

        return inflater.inflate(R.layout.fragment_next_event, container, false)
    }

    private fun initPresenter() {
        presenter = FootballPresenterImp(this,gson,api, this!!.context!!)

    }

    private fun initView() {
        presenter.getDataNextEvents("4328")

    }

    private fun partItemClicked(partItem : EventsItem) {

        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("dataParcel",partItem)
        startActivity(intent)


    }


    override fun berhasil(data: List<EventsItem>) {

    }

    override fun berhasilNextEvent(data: List<EventsItem>) {

        //masukkan ke adapter
        var NextEventAdapter = FootballAdapter(requireContext(), data, { partItem: EventsItem -> partItemClicked(partItem) })
        //adapter masukkan ke recyclerview
        recyclerview2.adapter = NextEventAdapter
        recyclerview2.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun gagal(pesan: String) {

    }

    companion object {
        fun newInstance(): FragmentNextEvent {
            val fragment = FragmentNextEvent()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
