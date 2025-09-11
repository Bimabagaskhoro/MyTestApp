package com.bimabk.mytestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bimabk.list.ListUsersFragment
import com.bimabk.mytestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, ListUsersFragment())
            .commit()
    }
}
