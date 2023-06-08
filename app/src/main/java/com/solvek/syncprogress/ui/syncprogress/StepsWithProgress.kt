package com.solvek.syncprogress.ui.syncprogress

import androidx.annotation.StringRes
import com.solvek.syncprogress.R

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

fun stepsWithOneProgress(@StringRes stepId: Int, progress: Float): List<StepProgress> {
    var stepFound = false
    return SYNC_WEIGHTS.mapIndexed{idx, weight ->
        if (SYNC_STEPS[idx] == stepId) {
            stepFound = true
            StepProgress(weight.toFloat(), progress)
        }
        else {
            StepProgress(weight.toFloat(), if (stepFound) 0f else 1f)
        }
    }
}

fun stepsWithSameProgress(progress: Float) =
    SYNC_WEIGHTS.map { StepProgress(it.toFloat(), progress) }