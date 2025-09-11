package com.bimabk.router

import android.content.Context
import android.content.Intent

interface RouteTo {

    fun routeToDetail(context: Context,  id: Int): Intent
}