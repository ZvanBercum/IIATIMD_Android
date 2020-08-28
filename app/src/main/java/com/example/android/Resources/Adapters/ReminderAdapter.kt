package com.example.android.Resources.Adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.Resources.Reminder.Reminder
import kotlinx.android.synthetic.main.reminder_card.view.*

class ReminderAdapter(private val reminders: ArrayList<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        private var view: View = v
        private var reminder: Reminder? = null

        init{
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("DEBUG", "Recycleviewclick")
        }

        companion object{
            private val REMINDER_KEY = "REMINDER"
        }

        fun bindReminder(reminder: Reminder){
            this.reminder = reminder
            view.Remindername.text = reminder.getMedName().toString()
            view.Reminderdosage.text = reminder.getDosage().toString()
            view.Reminderdate.text = reminder.getDate()
            view.Remindertime.text = reminder.getTime().toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflatedView = parent.inflate(R.layout.reminder_card, false)
        return ReminderViewHolder(inflatedView)
    }

    override fun getItemCount() = reminders.count()

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.bindReminder(reminder)
    }
}