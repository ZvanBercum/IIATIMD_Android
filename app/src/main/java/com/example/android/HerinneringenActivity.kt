package com.example.android

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.android.Resources.Api
import com.example.android.Resources.SharedPrefs

class HerinneringenActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("DEBUG", "GEKLIIKT!!!!!!!!!!!!!!!!!")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_herrineringen)
    }

}