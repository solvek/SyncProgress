package com.solvek.syncprogress

import android.app.Application
import com.solvek.syncprogress.ui.syncprogress.SyncMonitor

class SyncProgressApp: Application() {
    val syncMonitor by lazy {SyncMonitor(this)}

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SyncProgressApp
            private set
    }
}