package com.example.android.Resources

import android.app.Activity
import android.content.SharedPreferences

/**
 * @class SharedPrefs
 * This function fetches and saves objects in the storage of the device
 */
class SharedPrefs {
    //Init vars
    private var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    /**
     * @func shareSharedPrefs
     * @returns SharedPreferences
     * Loads the sharedpreferences so things can be fetched
     */
    fun shareSharedPrefs(): SharedPreferences {
        return this.activity.getSharedPreferences("MedicinePrefs",0);
    }


    /**
     * @func getLogged
     * @return Boolean
     * This function checks if the user is logged in
     */
    fun getLogged(): Boolean{
        val userPref = shareSharedPrefs()
        return userPref.getBoolean("Logged", false)
    }

    /**
     * WARNING THIS FUNCTION REMOVES THE COMPLETE APP MEMORY
     * @func removeAllPrefs
     * WARNING THIS FUNCTION REMOVES THE COMPLETE APP MEMORY
     */
    fun removeAllPrefs(){
        val preferences  = shareSharedPrefs()
        preferences.edit().clear().apply()
    }

}


