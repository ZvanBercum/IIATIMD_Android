package com.example.android.Resources.Database.Medicine

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R



class MedicineListAdapter(val context: Context) : RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var medicineList: List<Medicine>? = null

    fun setMedicineList(medicineList: List<Medicine>) {
        this.medicineList = medicineList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineListAdapter.MedicineViewHolder {
        val itemView = layoutInflater.inflate(R.layout.medicine_card, parent, false)
        return MedicineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicineListAdapter.MedicineViewHolder, position: Int) {
        medicineList?.let {
            val medicine = it[position]
            holder.medicineText.text = medicine.name
            holder.medicineDesc.text = medicine.desc
            Log.d("DEBUG", medicine.name.toString())
//            holder.itemView.setOnClickListener {
//                val dialogFragment: DialogFragment = MedicineSaveDialogFragment.newInstance(medicine.name)
//                dialogFragment.show(
//                    (context as AppCompatActivity).supportFragmentManager,
//                    MedicineSaveDialogFragment.TAG_DIALOG_MEDICINE_SAVE
//                )
//            }
        }
    }

    override fun getItemCount(): Int {
        return if (medicineList == null) {
            0
        } else {
            medicineList!!.size
        }
    }

    class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medicineText: TextView = itemView.findViewById(R.id.Medicinename)
        val medicineDesc: TextView = itemView.findViewById(R.id.Medicinedescription)
    }
}