package com.khairpurboyshostel.karachi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khairpurboyshostel.karachi.data.local.entities.*
import com.khairpurboyshostel.karachi.data.local.dao.*

@Database(
    entities = [
        StudentEntity::class,
        RoomEntity::class,
        PaymentEntity::class,
        ComplaintEntity::class,
        GatePassEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HostelDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun roomDao(): RoomDao
    abstract fun paymentDao(): PaymentDao
    abstract fun complaintDao(): ComplaintDao
    abstract fun gatePassDao(): GatePassDao

    companion object {
        @Volatile
        private var INSTANCE: HostelDatabase? = null

        fun getDatabase(context: Context): HostelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HostelDatabase::class.java,
                    "khairpur_hostel_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
