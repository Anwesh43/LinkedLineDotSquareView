package com.anwesh.uiprojects.linesquaredotview

/**
 * Created by anweshmishra on 18/07/19.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.app.Activity
import android.content.Context

val nodes : Int = 5
val lines : Int = 4
val balls : Int = 2
val scGap : Float = 0.05f
val scDiv : Double = 0.51
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val foreColor : Int = Color.parseColor("#4527A0")
val backColor : Int = Color.parseColor("#BDBDBD")
val rFactor : Float = 3.6f

fun Int.inverse() : Float = 1f / this
fun Float.scaleFactor() : Float = Math.floor(this / scDiv).toFloat()
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.mirrorValue(a : Int, b : Int) : Float {
    val k : Float = scaleFactor()
    return (1 - k) * a.inverse() + k * b.inverse()
}
fun Float.updateValue(dir : Float, a : Int, b : Int) : Float = mirrorValue(a, b) * dir * scGap

fun Canvas.drawLineSquareDot(i : Int, deg : Float, sc : Float, size : Float, paint : Paint) {
    save()
    rotate(deg)
    translate(0f, -size)
    drawLine(-size / 2, 0f, size / 2, 0f, paint)
    for (j in 0..(balls - 1)) {
        val scj : Float = sc.divideScale(j, balls)
        save()
        translate((size / 2) * (1 - 2 * j), 0f)
        drawCircle(0f, 0f, scj * (size / rFactor), paint)
        restore()
    }
    restore()
}

fun Canvas.drawLinesSquareDot(sc1 : Float, sc2 : Float, size : Float, paint : Paint) {
    var deg : Float = 0f
    for (j in 0..(lines - 1)) {
        val sc1j : Float = sc1.divideScale(j, lines)
        val sc2j : Float = sc2.divideScale(j, lines)
        deg = 90f * sc1j
        drawLineSquareDot(j, deg, sc2j, size, paint)
    }
}

fun Canvas.drawLSDNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val size : Float = gap / sizeFactor
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    paint.color = foreColor
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    save()
    translate(w / 2, gap * (i + 1))
    drawLinesSquareDot(sc1, sc2, size, paint)
    restore()
}

class LineSquareDotView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    
    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}
