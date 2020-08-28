package com.example.android.Resources

class Medicine {
    //Name of the user
    private var name: String? = null
    private var dosage: String? = null
    private var interval: String? = null
    private var time: String? = null
    private var start: String? = null
    private var end: String? = null

    constructor(name: String, dosage: String, interval: String, time: String, start: String, end: String){
        this.name = name
        this.dosage = dosage
        this.interval = interval
        this.time = time
        this.start = start
        this.end = end
    }

    fun getName(): String?{
        return this.name
    }



}