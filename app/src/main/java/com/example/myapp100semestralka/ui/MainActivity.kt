package com.example.myapp100semestralka.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log // Import Log class for logging
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapp100semestralka.R
import com.example.myapp100semestralka.data.database.TimerDatabase
import com.example.myapp100semestralka.data.entities.TimerEntity
import com.example.myapp100semestralka.databinding.ActivityMainBinding
import com.example.myapp100semestralka.service.TimerService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private lateinit var timerDatabase: TimerDatabase // Proměnná pro databázi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializace databáze
        timerDatabase = Room.databaseBuilder(
            applicationContext,
            TimerDatabase::class.java,
            "timer_database"
        ).build()

        // Nastavení posluchačů událostí tlačítek
        binding.startStopButton.setOnClickListener { startStopTimer() }
        binding.resetButton.setOnClickListener { resetTimer() }
        binding.saveButton.setOnClickListener { saveTimerToDatabase() }

        // Inicializace intentu pro službu a registrace broadcast receiveru
        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    // Funkce pro resetování časovače
    private fun resetTimer() {
        stopTimer()
        time = 0.0
        binding.timeTV.text = getTimeStringFromDouble(time)
    }

    // Funkce pro spuštění nebo zastavení časovače
    private fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
            // Uložení času do databáze po zastavení časovače
            saveTimerToDatabase()
        } else {
            startTimer()
        }
    }

    // Funkce pro spuštění časovače
    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.startStopButton.text = "Stop"
        binding.startStopButton.icon = getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true
    }

    // Funkce pro zastavení časovače
    private fun stopTimer() {
        stopService(serviceIntent)
        binding.startStopButton.text = "Start"
        binding.startStopButton.icon = getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
    }

    // Funkce pro uložení času do databáze
    private fun saveTimerToDatabase() {
        Log.d("MainActivity", "Ukládání do databáze") // Log message when saving to database
        GlobalScope.launch {
            timerDatabase.timerDao().insert(TimerEntity(timeInSeconds = time.roundToInt()))
        }
    }

    // BroadcastReceiver pro aktualizaci času
    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.timeTV.text = getTimeStringFromDouble(time)
        }
    }

    // Funkce pro formátování času z desetinného čísla na řetězec
    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    // Funkce pro vytvoření řetězce z hodin, minut a sekund
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)
}
