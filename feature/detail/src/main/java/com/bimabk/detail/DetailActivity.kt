package com.bimabk.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.bimabk.common.helper.Constant.USER_ID
import com.bimabk.detail.common.DetailNavigation
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class DetailActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getIntExtra(USER_ID, 0).toString()
        val finish = ::finish
        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                MaterialTheme {
                    val navController = rememberNavController()
                    DetailNavigation(
                        navController = navController,
                        userId = userId,
                        finish = finish
                    )
                }
            }
        }
    }
}