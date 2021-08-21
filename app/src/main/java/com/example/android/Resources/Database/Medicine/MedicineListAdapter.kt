package com.example.android.Resources.Database.Medicine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.*
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.MedicineListFragment.Companion.newInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*


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
            runBlocking {
                val medicineCount = withContext(Dispatchers.Default){
                    getFirstDosage(medicine)
                }
                holder.medicineCount.text = medicineCount ?: ""
                if(medicineCount == null || medicineCount == ""){
                    holder.medicineCount.visibility = View.GONE
                }
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(context, MedicineEditActivity::class.java)
                intent.putExtra("medicine", medicine.id.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (medicineList == null) {
            0
        } else {
            medicineList!!.size
        }
    }

    private suspend fun getFirstDosage(medicine: Medicine): String? {
        val timestamp = System.currentTimeMillis() / 1000
        Log.d("DEBUG", medicine.toString())
        val time = AppDatabase.getDatabase(context).dosageDao().firstDosageByMed(medicine.id, timestamp)
        if(time != null){
            val diff = time - timestamp
            if(diff <= 0) return ""
            //diff is a day or smaller
            if(diff <= 86400){
                val minutes = diff/60
                //Diff is an hour or bigger
                if(diff >= 3600){
                    val hours = minutes/60
                    return "$hours uur"
                }else{
                    return "$minutes min"
                }
            }else {
                val cal: Calendar = Calendar.getInstance()
                //Convert Epoch Unix Timestamp from seconds to milliseconds
                cal.timeInMillis = time*1000
                if(diff < 31556926){
                    val day = cal.get(Calendar.DATE)
                    val month = cal.get(Calendar.MONTH)
                    return "$day - $month"
                }else{
                    return cal.get(Calendar.YEAR).toString()
                }
            }
        }
        return ""
    }

    class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medicineText: TextView = itemView.findViewById(R.id.Medicinename)
        val medicineDesc: TextView = itemView.findViewById(R.id.Medicinedescription)
        val medicineCount: TextView = itemView.findViewById(R.id.Medicinetime)
    }
}