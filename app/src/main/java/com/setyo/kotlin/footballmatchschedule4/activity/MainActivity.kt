package com.setyo.kotlin.footballmatchschedule4.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import com.setyo.kotlin.footballmatchschedule4.fragment.FragmentNextEvent
import com.setyo.kotlin.footballmatchschedule4.R
import com.setyo.kotlin.footballmatchschedule4.fragment.FragmentFavoriteEvent
import com.setyo.kotlin.footballmatchschedule4.fragment.FragmentLastEvent

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    var content: FrameLayout? = null


    val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.last -> {
                val fragment = FragmentLastEvent.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.next -> {
                val fragment = FragmentNextEvent.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.fav -> {
                val fragment = FragmentFavoriteEvent.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        initView()
    }

    private fun initView() {


        //bottomnav
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragmentLastEvent.newInstance()
        addFragment(fragment)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
