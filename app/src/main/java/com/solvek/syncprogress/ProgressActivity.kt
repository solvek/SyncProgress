package com.solvek.syncprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.solvek.syncprogress.ui.syncprogress.MessageViewState
import com.solvek.syncprogress.ui.syncprogress.ProgressViewState
import com.solvek.syncprogress.ui.syncprogress.SyncViewState
import com.solvek.syncprogress.ui.theme.SyncProgressTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ProgressActivity : ComponentActivity() {
    private val syncViewState = MutableLiveData<SyncViewState>(MessageViewState(R.string.sync_step_ready, isAllComplete = false))
    private val showStartButton = MutableLiveData(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SyncProgressTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF002171)
                ) {
                    val syncViewState by syncViewState.observeAsState(null)
                    val showStartButton by showStartButton.observeAsState(false)
                    syncViewState?.let {
                        SyncScreen(
                            showStartButton = showStartButton,
                            syncViewState = it
                        )
                    }
                }
            }
        }

        lifecycleScope.launch {
            while(true){
                delay(TimeUnit.SECONDS.toMillis(1))
                showStartButton.value = false

                delay(2000)
                syncViewState.value = ProgressViewState(R.string.sync_step_initializing)
                delay(2000)
                syncViewState.value = ProgressViewState(R.string.sync_step_connecting)
                delay(2000)
                syncViewState.value = ProgressViewState(R.string.sync_step_reading)
                delay(2000)
                syncViewState.value = ProgressViewState(R.string.sync_step_configuring)

                val max = 20
                for (i in 1.. max) {
                    delay(500)
                    syncViewState.value = ProgressViewState(R.string.sync_step_retrieving, progress = 1f*i/max)
                }

                syncViewState.value = ProgressViewState(R.string.sync_step_uploading, 0.2f)
                delay(1000)
                syncViewState.value = ProgressViewState(R.string.sync_step_uploading, 0.6f)
                delay(1000)
                syncViewState.value = ProgressViewState(R.string.sync_step_uploading, 0.9f)
                delay(1000)

                syncViewState.value = MessageViewState(R.string.sync_step_complete)
                showStartButton.value = true
                delay(2000)
            }
        }
    }
}