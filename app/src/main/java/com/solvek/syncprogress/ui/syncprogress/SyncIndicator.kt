package com.solvek.syncprogress.ui.syncprogress

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solvek.syncprogress.R
import com.solvek.syncprogress.ui.theme.NegativeColor
import com.solvek.syncprogress.ui.theme.PositiveColor
import com.solvek.syncprogress.ui.theme.PositiveColorLight

@Composable
fun SyncIndicator(syncState: SyncState) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
        contentAlignment = Alignment.Center) {

        Image(
            painterResource(R.drawable.bg_sync_indicator),
            modifier = Modifier.fillMaxSize(),
            contentDescription="Background")

        when (syncState) {
            is MessageSyncState -> SyncIndicator(
                message = syncState.text,
                color = if (syncState.isPositive) PositiveColor else NegativeColor,
                allProgress = if (syncState.isAllComplete) 1f else 0f
            )

            is ProgressSyncState -> SyncIndicator(
                syncState.text,
                syncState.progress,
                PositiveColor,
                PositiveColorLight
            )
        }
    }
}

@Composable
private fun SyncIndicator(@StringRes message: Int, color: Color, allProgress: Float) {
    SyncIndicator(
        message,
        stepsWithSameProgress(allProgress),
        color = color,
        backColor = color)
}

@Composable
private fun SyncIndicator(@StringRes stepId: Int, progress: Float, color: Color, backColor: Color) {
    SyncIndicator(
        stepId,
        stepsWithOneProgress(stepId, progress),
        color,
        backColor)
}

@Composable
private fun SyncIndicator(
    @StringRes message: Int,
    steps: List<StepProgress>,
    color: Color,
    backColor: Color,) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(30.dp),
        contentAlignment = Alignment.Center) {

        Text(
            stringResource(message),
            color = color,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp),
            textAlign = TextAlign.Center)

        SyncProgress(steps, color, backColor)
    }
}