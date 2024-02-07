package com.example.myapp100semestralka.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp100semestralka.R
import com.example.myapp100semestralka.data.entities.TimerEntity

class TimerAdapter(private var timers: List<TimerEntity>) : RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {

    inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
    }

    // Metoda pro vytvoření nového ViewHolderu
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_item, parent, false)
        return TimerViewHolder(view)
    }

    // Metoda pro vazbu dat na konkrétní položku ve View Holderu
    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val timer = timers[position]
        val formattedTime = formatTime(timer.timeInSeconds)
        holder.timeTextView.text = formattedTime
    }

    // Metoda pro získání počtu položek v seznamu
    override fun getItemCount(): Int {
        return timers.size
    }

    // Funkce pro formátování času na řetězec ve formátu "hh:mm:ss"
    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }

    // Funkce pro aktualizaci dat v adaptéru
    fun updateData(newTimers: List<TimerEntity>) {
        timers = newTimers.sortedByDescending { it.id } // Seřadit podle ID sestupně
        notifyDataSetChanged()
    }
}
