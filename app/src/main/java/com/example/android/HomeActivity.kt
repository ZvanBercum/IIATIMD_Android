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

                    val button = findViewById<Button>(R.id.test_button)

                    //Submit login
                    button.setOnClickListener{
                        Log.d("DEBUG", "GEKLIIKT!!!!!!!!!!!!!!!!!")

                        // TODO laad herrineringenActivity view in als er op de button wordt geklikt
                        val intent = Intent(this, HerinneringenActivity::class.java)
                        startActivity(intent)
                    }

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