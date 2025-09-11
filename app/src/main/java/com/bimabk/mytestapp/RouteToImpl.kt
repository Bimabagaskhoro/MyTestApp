package com.bimabk.mytestapp

import android.content.Context
import android.content.Intent
import com.bimabk.common.helper.Constant.USER_ID
import com.bimabk.detail.DetailActivity
import com.bimabk.router.RouteTo

class RouteToImpl: RouteTo {
    override fun routeToDetail(context: Context, id: Int): Intent {
        return Intent(context, DetailActivity::class.java).apply {
            putExtra(USER_ID, id)
        }
    }
}