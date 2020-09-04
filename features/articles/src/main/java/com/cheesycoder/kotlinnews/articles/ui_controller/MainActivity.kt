package com.cheesycoder.kotlinnews.articles.ui_controller

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.cheesycoder.kotlinnews.articles.R

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
