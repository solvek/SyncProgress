package com.solvek.syncprogress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solvek.syncprogress.ui.syncprogress.MessageSyncReady
import com.solvek.syncprogress.ui.syncprogress.ProgressSyncState
import com.solvek.syncprogress.ui.syncprogress.SyncMonitor
import com.solvek.syncprogress.ui.syncprogress.SyncState

class SyncViewModel: ViewModel() {
    private val syncMonitor = SyncProgressApp.instance.syncMonitor
    private lateinit var subscription: SyncMonitor.Subscription

    val syncState = MutableLiveData<SyncState>(MessageSyncReady)
    val showStartButton = MutableLiveData(true)

    fun startStateMonitoring(){
        subscription = syncMonitor.subscribe(this::populateState)
        populateState()
    }

    fun stopStateMonitoring(){
        if (!this::subscription.isInitialized) return
        syncMonitor.unsubscribe(subscription)
    }

    private fun populateState(){
        syncState.value = syncMonitor.syncState
        showStartButton.value = syncMonitor.syncState !is ProgressSyncState
    }
}