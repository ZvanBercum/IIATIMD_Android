package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.Resources.SharedPrefs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Load class for memory access
        val sharedPrefs = SharedPrefs(this)

        //Check if user is logged in
        val logged = sharedPrefs.isLogged()
        if(logged != null){
            this.switchToHome()
        }else{
            this.switchToLogin()
        }
    }

    /**
     * @func switchToLogin
     * Load the login screen
     */
    fun switchToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    /**
     * @func switchToHome
     * Load the home screen if user is logged
     */
    fun switchToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


}