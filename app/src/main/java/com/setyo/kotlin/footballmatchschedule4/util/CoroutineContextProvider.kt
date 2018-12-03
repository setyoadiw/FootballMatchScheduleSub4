package com.setyo.kotlin.footballmatchschedule4.util

import kotlinx.coroutines.Dispatchers

import kotlin.coroutines.CoroutineContext


open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
//    open val IO: CoroutineContext by lazy { CommonPool }
}