package com.solvek.syncprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.solvek.syncprogress.ui.syncprogress.StepProgress
import com.solvek.syncprogress.ui.syncprogress.SyncProgress
import com.solvek.syncprogress.ui.theme.NegativeColor
import com.solvek.syncprogress.ui.theme.PositiveColor
import com.solvek.syncprogress.ui.theme.PositiveColorLight
import com.solvek.syncprogress.ui.theme.SyncProgressTheme

class ProgressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SyncProgressTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SyncProgress(R.string.sync_step_retrieving,
                        progress = 0.25f,
                        PositiveColor,
                        PositiveColorLight)
                }
            }
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
fun SyncProgress(@StringRes message: Int, color: Color) {
    SyncProgress(
        steps = SYNC_WEIGHTS.map { StepProgress(it.toFloat()) },
        color = color,
        backColor = color)
}

@Composable
fun SyncProgress(@StringRes stepId: Int, progress: Float, color: Color, backColor: Color) {
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SyncProgressTheme {
        SyncProgress(
            R.string.sync_step_retrieving,
            progress = 0.25f,
            PositiveColor,
            PositiveColorLight)
    }
}