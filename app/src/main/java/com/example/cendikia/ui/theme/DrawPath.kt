package com.example.cendikia.ui.theme


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun WaveBackground1() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Define wave paths
        val path1 = createWavePath(
            startY = size.height * 0.7f,
            controlPoint1 = Offset(size.width * 0.25f, size.height * 0.5f),
            controlPoint2 = Offset(size.width * 0.75f, size.height * 0.9f),
            endY = size.height
        )

        val path2 = createWavePath(
            startY = size.height * 0.7f,
            controlPoint1 = Offset(size.width * 0.25f, size.height * 0.5f),
            controlPoint2 = Offset(size.width * 0.75f, size.height * 0.9f),
            endY = size.height
        )

        // Convert Color to Paint and draw each wave path
        drawIntoCanvas { canvas ->
            val paint1 = Paint().apply { color = Color(0xFF821FDA) }
            val paint2 = Paint().apply { color = Color(0xFFF8F5FC) }

            canvas.nativeCanvas.drawPath(path1.asAndroidPath(), paint1.asFrameworkPaint())
            canvas.nativeCanvas.drawPath(path2.asAndroidPath(), paint2.asFrameworkPaint())
        }
    }
}



@Composable
fun WaveBackground2() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Define wave paths
        val path1 = createWavePath(
            startY = size.height * 0.7f,
            controlPoint1 = Offset(size.width * 0.25f, size.height * 0.5f),
            controlPoint2 = Offset(size.width * 0.75f, size.height * 0.9f),
            endY = size.height
        )

        val path2 = createWavePath(
            startY = size.height * 0.7f,
            controlPoint1 = Offset(size.width * 0.25f, size.height * 0.5f),
            controlPoint2 = Offset(size.width * 0.75f, size.height * 0.9f),
            endY = size.height
        )

        // Convert Color to Paint and draw each wave path
        drawIntoCanvas { canvas ->
            val paint1 = Paint().apply { color = Color(0xFFF8F5FC) }
            val paint2 = Paint().apply { color = Color(0xFF821FDA) }

            canvas.nativeCanvas.drawPath(path1.asAndroidPath(), paint1.asFrameworkPaint())
            canvas.nativeCanvas.drawPath(path2.asAndroidPath(), paint2.asFrameworkPaint())
        }
    }
}

// Function to create a wave path
fun DrawScope.createWavePath(
    startY: Float,
    controlPoint1: Offset,
    controlPoint2: Offset,
    endY: Float
): Path {
    return Path().apply {
        moveTo(0f, startY)
        quadraticBezierTo(
            controlPoint1.x, controlPoint1.y,
            size.width * 0.5f, startY
        )
        quadraticBezierTo(
            controlPoint2.x, controlPoint2.y,
            size.width, startY
        )
        lineTo(size.width, endY)
        lineTo(0f, endY)
        close()
    }
}

@Preview
@Composable
fun PreviewWaveBackground() {
    MaterialTheme {
        WaveBackground1()
    }
}
