package com.example.android.Resources

class Medicine {

    private var name: String? = null
    private var dosage: String? = null

    constructor(name: String, dosage: String){
        this.name = name
        this.dosage = dosage
    }

    fun getName(): String?{
        return this.name
    }

    fun getDosage(): String?{
        return this.dosage
    }


}