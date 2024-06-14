package com.example.cropdoc.activity_dashboard.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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

@Entity(
    tableName = "daily-activity-table",
    foreignKeys = [ForeignKey(
        entity = PlantingActivity::class,
        parentColumns = ["id"],
        childColumns = ["plantingActivityId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DailyActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="plantingActivityId")
    val plantingActivityId: Long,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name="description")
    val description: String,
    @ColumnInfo(name="date")
    val date: String
)