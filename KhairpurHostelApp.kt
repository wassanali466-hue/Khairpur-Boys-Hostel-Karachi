package com.khairpurboyshostel.karachi

import android.app.Application
import com.khairpurboyshostel.karachi.data.local.HostelDatabase
import com.khairpurboyshostel.karachi.security.SecurityManager

class KhairpurHostelApp : Application() {

    val database: HostelDatabase by lazy {
        HostelDatabase.getDatabase(this)
    }

    val securityManager: SecurityManager by lazy {
        SecurityManager(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}
