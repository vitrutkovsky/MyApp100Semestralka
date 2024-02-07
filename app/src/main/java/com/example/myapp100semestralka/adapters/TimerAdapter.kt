package com.example.myapp100semestralka.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp100semestralka.data.entities.TimerEntity
import com.example.myapp100semestralka.R

class TimerAdapter(private val timers: List<TimerEntity>) : RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {

    inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val timer = timers[position]
        holder.timeTextView.text = timer.timeInSeconds.toString()
    }

    override fun getItemCount(): Int {
        return timers.size
    }
}
