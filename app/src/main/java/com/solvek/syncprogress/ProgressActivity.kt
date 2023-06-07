package com.solvek.syncprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.solvek.syncprogress.ui.theme.SyncProgressTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProgressActivity : ComponentActivity() {
    private val model = SyncViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SyncProgressTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF002171)
                ) {
                    SyncScreen(model)
                }
            }
        }
        model.startStateMonitoring()

        lifecycleScope.launch {
            while(true){
                simulateCycle()
            }
        }
    }

    override fun onDestroy() {
        model.stopStateMonitoring()
        super.onDestroy()
    }

    private suspend fun simulateCycle() {
        val syncMonitor = SyncProgressApp.instance.syncMonitor

        syncMonitor.setStateReady()
        delay(TimeUnit.SECONDS.toMillis(1))

        delay(2000)
        syncMonitor.setStateProgressStep(R.string.sync_step_initializing)
        delay(2000)
        syncMonitor.setStateProgressStep(R.string.sync_step_connecting)
        delay(2000)
        syncMonitor.setStateProgressStep(R.string.sync_step_reading)
        delay(2000)
        syncMonitor.setStateProgressStep(R.string.sync_step_configuring)

        val max = 20
        for (i in 1..max) {
            delay(500)
            syncMonitor.setStateProgressStep(R.string.sync_step_retrieving, progress = 1f * i / max)
        }

        syncMonitor.setStateProgressStep(R.string.sync_step_uploading, 0.2f)
        delay(1000)
        syncMonitor.setStateProgressStep(R.string.sync_step_uploading, 0.6f)
        delay(1000)
        syncMonitor.setStateProgressStep(R.string.sync_step_uploading, 0.9f)
        delay(1000)

        syncMonitor.setStateComplete()
        delay(2000)

        listOf(
            R.string.sync_fail_init,
            R.string.sync_fail_connection,
            R.string.sync_fail_reading,
            R.string.sync_fail_configuring,
            R.string.sync_fail_retrieving,
            R.string.sync_fail_uploading
        ).forEach { message ->
            syncMonitor.setStateError(message)
            delay(1000)
        }
    }
}