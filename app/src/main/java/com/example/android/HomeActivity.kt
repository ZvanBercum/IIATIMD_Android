package com.example.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.android.Resources.Api
import com.example.android.Resources.SharedPrefs


class HomeActivity : AppCompatActivity() {

    //Init view
    lateinit var api : Api
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        this.api = Api(this)
        this.sharedPrefs = SharedPrefs(this)

        val reminderButton = findViewById<Button>(R.id.reminder_button)

        val medicineButton = findViewById<Button>(R.id.medicine_button)

        //Check if user is logged in
        val token = this.sharedPrefs.isLogged()
        if(token != null){
            reminderButton.setOnClickListener{
                this.goToReminders()
            }
            medicineButton.setOnClickListener{
                this.goToMedicines()
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
        val intent = Intent(this, ReminderActivity::class.java)
        startActivity(intent)
    }

    private fun goToMedicines(){
        val intent = Intent(this, MedicineActivity::class.java)
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