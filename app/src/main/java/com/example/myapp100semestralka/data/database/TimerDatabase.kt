package com.example.myapp100semestralka.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp100semestralka.data.dao.TimerDao
import com.example.myapp100semestralka.data.entities.TimerEntity

@Database(entities = [TimerEntity::class], version = 1, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
}