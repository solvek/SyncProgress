package com.solvek.syncprogress.ui.syncprogress

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.solvek.syncprogress.R
import com.solvek.syncprogress.ui.theme.NegativeColor
import com.solvek.syncprogress.ui.theme.PositiveColor
import com.solvek.syncprogress.ui.theme.PositiveColorLight

@Composable
fun SyncIndicator(syncViewState: SyncViewState) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        contentAlignment = Alignment.Center) {

        when(syncViewState){
            is MessageViewState -> SyncIndicator(
                message = syncViewState.text,
                color = if (syncViewState.isPositive) PositiveColor else NegativeColor,
                allProgress = if (syncViewState.isAllComplete) 1f else 0f
            )
            is ProgressViewState -> SyncIndicator(
                syncViewState.text,
                syncViewState.progress,
                PositiveColor,
                PositiveColorLight)
        }
    }
}

private val SYNC_STEPS = listOf(
    R.string.sync_step_initializing,
    R.string.sync_step_connecting,
    R.string.sync_step_reading,
    R.string.sync_step_configuring,
    R.string.sync_step_retrieving,
    R.string.sync_step_uploading,
)
private val SYNC_WEIGHTS = listOf(
    10,
    15,
    10,
    10,
    40,
    30
)

@Composable
fun SyncIndicator(@StringRes message: Int, color: Color, allProgress: Float) {
    SyncProgress(
        steps = SYNC_WEIGHTS.map { StepProgress(it.toFloat(), allProgress) },
        color = color,
        backColor = color)
}

@Composable
fun SyncIndicator(@StringRes stepId: Int, progress: Float, color: Color, backColor: Color) {
    var stepFound = false
    val steps = SYNC_WEIGHTS.mapIndexed{idx, weight ->
        if (SYNC_STEPS[idx] == stepId) {
            stepFound = true
            StepProgress(weight.toFloat(), progress)
        }
        else {
            StepProgress(weight.toFloat(), if (stepFound) 0f else 1f)
        }
    }

    SyncProgress(
        steps,
        color,
        backColor)
}