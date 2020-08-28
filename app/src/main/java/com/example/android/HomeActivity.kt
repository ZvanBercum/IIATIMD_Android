package com.example.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.example.android.Resources.Api
import com.example.android.Resources.SharedPrefs
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONArray
import org.json.JSONObject


class HomeActivity : AppCompatActivity() {

    //Init view
    lateinit var api : Api
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        this.api = Api(this)
        this.sharedPrefs = SharedPrefs(this)

        val reminderButton = findViewById<Button>(R.id.test_button)

        //Check if user is logged in
        val token = this.sharedPrefs.isLogged()
        if(token != null){
            reminderButton.setOnClickListener{
                this.goToReminders()
            }
        }else{
            logout()
        }
    }

    /**
     * @func logout
     * This function switches to home
     */
    private fun logout() {
        this.sharedPrefs.logoutUser()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    /**
     * @func goToReminders
     * This function switches to the reminder activity
     */
    private fun goToReminders(){
        val intent = Intent(this, HerinneringenActivity::class.java)
        startActivity(intent)
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