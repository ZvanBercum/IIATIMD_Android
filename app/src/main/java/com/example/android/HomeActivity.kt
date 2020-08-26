package com.example.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    /**
     * OVERIDE
     * @func onBackPressed
     * This function makes the user stay logged in
     */
    override fun onBackPressed(){
//        super.onBackPressed()
        finishAffinity()
    }
}