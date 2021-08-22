package com.example.android.Resources.Database.Dosage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.*
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.MedicineListFragment.Companion.newInstance
import kotlinx.coroutines.*
import java.util.*


class DosageListAdapter(val context: Context) : RecyclerView.Adapter<DosageListAdapter.DosageViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var dosageList: List<Dosage>? = null

    fun setDosageList(dosageList: List<Dosage>, medId: Long) {
        this.dosageList = dosageList.filter { it.medicineId == medId }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DosageListAdapter.DosageViewHolder {
        val itemView = layoutInflater.inflate(R.layout.dosage_card, parent, false)
        return DosageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DosageListAdapter.DosageViewHolder, position: Int) {
        dosageList?.let {
            val dosage = it[position]
            holder.dosageDate.text = makeDate(dosage.date)
            holder.dosageText.text = dosage.dosage
            holder.deleteBtn.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) { deleteDosage(dosage.id) }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (dosageList == null) {
            0
        } else {
            dosageList!!.size
        }
    }

    private fun makeDate(timestamp: Long): String{
        val cal: Calendar = Calendar.getInstance()
        //Convert Epoch Unix Timestamp from seconds to milliseconds
        cal.timeInMillis = timestamp*1000
        var hour = cal.get(Calendar.HOUR_OF_DAY)
        var minute = cal.get(Calendar.MINUTE)
        val day = cal.get(Calendar.DATE)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        var stringHour = ""
        var stringMinute = ""
        if(hour < 10){
            stringHour = "0"+hour
        }else stringHour = hour.toString()
        if(minute < 10){
            stringMinute = "0"+minute
        }else stringMinute = minute.toString()
        return "$stringHour:$stringMinute  $day-$month-$year"
    }

    private suspend fun deleteDosage(id: Long) {
        val dosageDao = AppDatabase.getDatabase(context).dosageDao()
        dosageDao.deleteById(id)
    }

    class DosageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dosageText: TextView = itemView.findViewById(R.id.dosageDosage)
        val dosageDate: TextView = itemView.findViewById(R.id.dosageDate)
        val deleteBtn: TextView = itemView.findViewById(R.id.dosageRemove)
    }
}