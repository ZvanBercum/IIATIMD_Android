package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.AuthFailureError
import com.example.android.Resources.Api
import com.example.android.Resources.Medicine
import com.example.android.Resources.SharedPrefs
import org.json.JSONArray
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    lateinit var api : Api
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.api = Api(this)
        this.sharedPrefs = SharedPrefs(this)

        val token = this.sharedPrefs.isLogged()
        if(token != null){
            this.api.getMedicines(token){ result, error ->
                if(error != null){
                    //TODO: Handle other errors
                    if(error is AuthFailureError){
                        logout()
                    }
                }
                if(result != null){
                    //TODO Start of medicine fetching, needs to move to its own class
                    val medicinesArray = JSONArray(result)
                    val medicines = JSONObject(JSONArray(medicinesArray[0].toString())[0].toString())
                    Log.d("DEBUG", medicines.toString())
                    Log.d("DEBUG", medicines["naam"].toString())
                }

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
     * OVERIDE
     * @func onBackPressed
     * This function makes the user stay logged in
     */
    override fun onBackPressed(){
//        super.onBackPressed()
        finishAffinity()
    }
}