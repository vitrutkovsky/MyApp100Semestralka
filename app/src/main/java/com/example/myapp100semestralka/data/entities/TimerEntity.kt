package com.example.myapp100semestralka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timeInSeconds: Int
)