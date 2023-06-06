package com.solvek.syncprogress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solvek.syncprogress.ui.syncprogress.ProgressViewState
import com.solvek.syncprogress.ui.syncprogress.SyncIndicator
import com.solvek.syncprogress.ui.syncprogress.SyncViewState
import com.solvek.syncprogress.ui.theme.NegativeColor
import com.solvek.syncprogress.ui.theme.SyncProgressTheme

@Composable
fun SyncScreen(showStartButton: Boolean, syncViewState: SyncViewState){
    Column(Modifier.fillMaxSize().padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.size(20.dp))
        Text(stringResource(R.string.ecg_capture), fontSize = 40.sp, color = Color.White)
        Text(stringResource(R.string.from_chest_patch), fontSize = 20.sp, color = NegativeColor)
        Spacer(modifier = Modifier.size(20.dp))
        SyncIndicator(syncViewState)
        if (showStartButton){
            Spacer(modifier = Modifier.size(40.dp))
            Button(onClick = {}) {
                Text(stringResource(R.string.start))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SyncProgressTheme {
        SyncScreen(
            true,
            ProgressViewState(R.string.sync_step_retrieving, 0.25f)
        )
    }
}
