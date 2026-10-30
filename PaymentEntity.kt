package com.khairpurboyshostel.karachi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey
    val id: String,
    val receiptNo: String,
    val studentId: String,
    val amount: Double,
    val paymentDate: String,
    val feeMonth: String,
    val method: String, // Cash, JazzCash, Easypaisa, Other
    val transactionRef: String? = null,
    val status: String = "verified",
    val verifiedBy: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
