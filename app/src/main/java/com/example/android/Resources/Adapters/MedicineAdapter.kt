package com.example.android.Resources.Adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.Resources.Medicine.Medicine
import com.example.android.Resources.Reminder.Reminder
import kotlinx.android.synthetic.main.medicine_card.view.*
import kotlinx.android.synthetic.main.reminder_card.view.*


class MedicineAdapter(private val medicines: ArrayList<Medicine>) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    class MedicineViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        private var view: View = v
        private var medicine: Medicine? = null

        init{
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("DEBUG", "Recycleviewclick")
        }

        companion object{
            private val REMINDER_KEY = "MEDICINE"
        }

        fun bindMedicine(medicine: Medicine){
            this.medicine = medicine
            view.Medicinename.text = medicine.name.toString()
            view.Medicinedosage.text = medicine.dosage.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val inflatedView = parent.inflate(R.layout.reminder_card, false)
        return MedicineViewHolder(inflatedView)
    }

    override fun getItemCount() = medicines.count()

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicines[position]
        holder.bindMedicine(medicine)
    }
}