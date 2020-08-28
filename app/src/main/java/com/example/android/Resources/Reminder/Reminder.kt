package com.example.android.Resources.Reminder

import com.example.android.Resources.Medicine.Medicine

class Reminder {
    private var uuid: String? = null
    private lateinit var medicine: Medicine
    private var time: String? = null
    private var date: String? = null


    constructor(uuid: String, time: String, date: String, medId: String){
        this.uuid = uuid;
        this.time = time
        this.date = date
    }

    fun getMedName(): String?{
        return "Paracetamol"
    }

    fun getDosage(): String?{
        return "500mg"
    }

    fun getTime(): String?{
        return this.time
    }

    fun getDate(): String?{
        return this.date
    }


}