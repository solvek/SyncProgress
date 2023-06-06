package com.solvek.syncprogress.ui.syncprogress

import androidx.annotation.StringRes

sealed class SyncViewState

data class MessageViewState(@StringRes val text: Int, val isAllComplete: Boolean = true, val isPositive: Boolean = true): SyncViewState()

data class ProgressViewState(@StringRes val text: Int, val progress: Float = 0.5f): SyncViewState()
