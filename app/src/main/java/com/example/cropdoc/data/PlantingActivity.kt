package com.example.cropdoc.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDate

@Entity(tableName = "planting-activity-table")
data class PlantingActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="plant-name")
    val plantName: String="",
    @ColumnInfo(name="no-of-plants")
    val noOfPlants:String="",
    @ColumnInfo(name="date")
    val date:String = "",
    @ColumnInfo(name="harvest-weeks")
    val harvestWeeks:String=""
)