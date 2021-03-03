package com.suy.squizwardapp.base

import android.app.Application
import com.suy.squizwardapp.data.database.DatabaseManager

class BaseApp : Application() {
    override fun onCreate() {
        DatabaseManager.initiate(this)
        super.onCreate()
    }
}