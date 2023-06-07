package com.solvek.syncprogress.ui.syncprogress

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.StringRes
import com.solvek.syncprogress.R

class SyncMonitor(private val context: Context) {
    private var _syncState: SyncState = MessageSyncReady
    var syncState: SyncState
        get() = _syncState
        private set(value) {
            _syncState = value
            context.sendBroadcast(Intent(ON_SYNC_STATE_CHANGED_ACTION))
        }

    fun setStateReady(){
        syncState = MessageSyncReady
    }

    fun setStateComplete(){
        syncState = MessageSyncState(R.string.sync_step_complete)
    }

    fun setStateProgressStep(@StringRes stepId: Int, progress: Float = 0.5f){
        syncState = ProgressSyncState(stepId, progress)
    }

    fun setStateError(@StringRes errorMessage: Int){
        syncState = MessageSyncState(errorMessage, isPositive = false)
    }

    fun subscribe(onStateChanged: () -> Unit): Subscription{
        val receiver = object:BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (ON_SYNC_STATE_CHANGED_ACTION == intent?.action) onStateChanged()
            }
        }

        context.registerReceiver(receiver, IntentFilter(ON_SYNC_STATE_CHANGED_ACTION))
        return Subscription(receiver)
    }

    fun unsubscribe(subscription: Subscription){
        context.unregisterReceiver(subscription.receiver)
    }

    class Subscription internal constructor(internal val receiver: BroadcastReceiver)

    companion object {
        private val ON_SYNC_STATE_CHANGED_ACTION = "${SyncMonitor::javaClass.name}.SYNC_STATE_CHANGED_ACTION"
    }
}