package com.solvek.syncprogress.ui.syncprogress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val GAP_ANGLE = 5f

@Composable
fun SyncProgress(
    steps: List<StepProgress>,
    color: Color,
    backColor: Color,
    modifier: Modifier = Modifier,
    startAngle: Float = 270f,
    strokeWidth: Dp = 5.dp
){
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }
    val c = steps.size
    val totalWeight = steps.sumOf { it.weight.toDouble() }.toFloat()
    val totalProgress = steps.sumOf { it.progress.toDouble() }.toFloat()/c
    val sweepFactor = (360f - c*GAP_ANGLE)/totalWeight
    Canvas(
        modifier
            .progressSemantics(totalProgress)
            .fillMaxSize()
    ) {
        var angle = startAngle + GAP_ANGLE / 2
        for(sp: StepProgress in steps){
            val sweep = sp.weight * sweepFactor
            drawSyncProgressSegment(
                angle,
                sweep,
                color,
                backColor,
                stroke,
                sp.progress)
            angle += GAP_ANGLE + sweep
        }
    }
}

private fun DrawScope.drawSyncProgressSegment(
    startAngle: Float,
    sweep: Float,
    color: Color,
    backColor: Color,
    stroke: Stroke,
    progress: Float
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    val sweep1 = sweep * progress
    val sweep2 = sweep - sweep1
    val startAngle2 = startAngle + sweep1
    rotate(degrees = -90f) {
        drawArc(
            brush = Brush.sweepGradient(
                colorStops = listOf(
                    0.0f to color,
                    sweep1 / 360 to backColor,
                ).toTypedArray()
            ),
            startAngle = startAngle + 90,
            sweepAngle = sweep1,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = stroke
        )
    }
    drawArc(
        color = backColor,
        startAngle = startAngle2,
        sweepAngle = sweep2,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}