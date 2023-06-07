package com.solvek.syncprogress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solvek.syncprogress.ui.syncprogress.ProgressSyncState
import com.solvek.syncprogress.ui.syncprogress.SyncIndicator
import com.solvek.syncprogress.ui.syncprogress.SyncState
import com.solvek.syncprogress.ui.theme.NegativeColor
import com.solvek.syncprogress.ui.theme.SyncProgressTheme

@Composable
fun SyncScreen(model: SyncViewModel){
    val syncViewState by model.syncState.observeAsState(null)
    val showStartButton by model.showStartButton.observeAsState(false)

    syncViewState?.let {
        SyncScreen(
            showStartButton = showStartButton,
            syncState = it
        )
    }
}
@Composable
private fun SyncScreen(showStartButton: Boolean, syncState: SyncState){
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.size(30.dp))
        Text(stringResource(R.string.ecg_capture), fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Text(stringResource(R.string.from_chest_patch), fontSize = 20.sp, color = NegativeColor, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(30.dp))
        SyncIndicator(syncState)
        if (showStartButton){
            Spacer(modifier = Modifier.size(50.dp))
            Button(onClick = {},  colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7DC270))) {
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
            ProgressSyncState(R.string.sync_step_retrieving, 0.25f)
        )
    }
}
