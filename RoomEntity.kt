package com.khairpurboyshostel.karachi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey
    val id: String,
    val roomNumber: String,
    val floor: String,
    val capacity: Int = 5,
    val description: String? = null,
    val isActive: Boolean = true
)
