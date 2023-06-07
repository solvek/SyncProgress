package com.solvek.syncprogress.ui.syncprogress

import androidx.annotation.StringRes
import com.solvek.syncprogress.R

sealed class SyncState

data class MessageSyncState(@StringRes val text: Int, val isAllComplete: Boolean = true, val isPositive: Boolean = true): SyncState()

data class ProgressSyncState(@StringRes val text: Int, val progress: Float = 0.5f): SyncState()

val MessageSyncReady = MessageSyncState(R.string.sync_step_ready, isAllComplete = false)
