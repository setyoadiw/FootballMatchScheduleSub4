package com.setyo.kotlin.footballmatchschedule4.util


import kotlin.coroutines.CoroutineContext
import com.setyo.kotlin.footballmatchschedule4.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TestCoroutineContextProvider : CoroutineContextProvider(){
//    override val main: CoroutineContext = Unconfined
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined

//    override val IO: CoroutineContext = Unconfined

}