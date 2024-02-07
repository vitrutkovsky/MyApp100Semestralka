package com.example.myapp100semestralka.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapp100semestralka.data.entities.TimerEntity

@Dao
interface TimerDao {
    @Insert
    suspend fun insert(timer: TimerEntity)

    @Query("SELECT * FROM timers ORDER BY id DESC")
    suspend fun getAllTimers(): List<TimerEntity>
}