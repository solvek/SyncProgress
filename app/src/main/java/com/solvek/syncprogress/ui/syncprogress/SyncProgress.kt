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

private const val GAP_ANGLE = 2f

@Composable
fun SyncProgress(
    steps: List<StepProgress>,
    color: Color,
    backColor: Color,
    modifier: Modifier = Modifier,
    startAngle: Float = 270f,
    strokeWidth: Dp = 10.dp
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
//            .background(Color.Red)
    ) {
        var angle = startAngle + GAP_ANGLE / 2

//        drawSyncProgressSegment(
//            angle,
//            50f,
//            color,
//            backColor,
//            stroke,
//            1f)

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
    val width = size.width
    val diameterOffset = stroke.width / 2
    val arcDimen = width - 2 * diameterOffset
    val center = Offset(width/2, width/2)
    val sweepPart = sweep/360f
    val point1 = pointFun(progress, 0.05f*sweepPart)
    val point2 = if (progress == 0f || progress == 1f){
        progress
    }
    else {
        sweepPart
    }
    rotate(
        degrees = startAngle,
        pivot = center) {
        drawArc(
            brush = Brush.sweepGradient(
                colorStops = listOf(
                    0f to color,
                    point1 to color,
//                    point2 to backColor,
                    1f to backColor,
                ).toTypedArray(),
                center = center
            ),
            startAngle = 0f,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = stroke
        )
    }
}

private fun pointFun(x: Float, c: Float) = x*(2*(1-c)*x+2*c-1)