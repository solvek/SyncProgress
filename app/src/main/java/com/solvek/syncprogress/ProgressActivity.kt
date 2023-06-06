package com.solvek.syncprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solvek.syncprogress.ui.syncprogress.ProgressViewState
import com.solvek.syncprogress.ui.syncprogress.StepProgress
import com.solvek.syncprogress.ui.syncprogress.SyncProgress
import com.solvek.syncprogress.ui.theme.NegativeColor
import com.solvek.syncprogress.ui.theme.PositiveColor
import com.solvek.syncprogress.ui.theme.PositiveColorLight
import com.solvek.syncprogress.ui.theme.SyncProgressTheme

class ProgressActivity : ComponentActivity() {
//    private var progress = MutableLiveData(0f)
//        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SyncProgressTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF002171)
                ) {
                    //                        val p by progress.observeAsState(0f)
                    SyncScreen(
                        showStartButton = true,
                        syncViewState = ProgressViewState(R.string.sync_step_retrieving, 0.25f)
                    )
                }
            }
        }

//        lifecycleScope.launch {
//            while(true){
//                val p = (progress.value ?: 0f) + 0.05f
//
//                if (p >= 0.1f){
//                    progress.value = 1f
//                    delay(3000)
//                    progress.value = 0f
//                    delay(3000)
//                }
//                else {
//                    progress.value = p
//                    delay(1000)
//                }
//            }
//        }
    }
}