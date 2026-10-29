package com.khairpurboyshostel.karachi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey
    val id: String, // e.g., KBH-2026-001
    val fullName: String,
    val fatherName: String,
    val studentMobile: String,
    val guardianMobile: String,
    val college: String,
    val course: String,
    val semester: String,
    val cnic: String?,
    val homeAddress: String,
    val admissionDate: String,
    val roomId: String,
    val bedNumber: Int,
    val monthlyFee: Double = 14000.0,
    val securityDeposit: Double = 10000.0,
    val photoUrl: String? = null,
    val notes: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
