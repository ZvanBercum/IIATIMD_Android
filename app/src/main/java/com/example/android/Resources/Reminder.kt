package com.example.android.Resources

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Reminder {
    private var uuid: String? = null
    private lateinit var medicine: Medicine
    private var dosage: String? = null
    private var interval: String? = null
    private var time: String? = null
    private var date: String? = null


    constructor(uuid: String, time: String, date: String, medId: String){
        this.uuid = uuid;
        this.time = time
        this.date = date
        this.medicine = Medicine("Paracetamol", "500mg")
    }

    fun getMedName(): String?{
        return this.medicine.getName()
    }

    fun getDosage(): String?{
        return this.medicine.getDosage()
    }

    fun getTime(): String?{
        return this.time
    }

    fun getDate(): String?{
        return this.date
    }


}