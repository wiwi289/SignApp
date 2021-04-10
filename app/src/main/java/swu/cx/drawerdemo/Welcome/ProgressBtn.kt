package swu.cx.drawerdemo.Welcome

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import swu.cx.drawerdemo.R

class ProgressBtn : View {
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?):super(context,attrs)
    var lshortenx =0f
        set(value) {
            field = value
            invalidate()
        }
    var rshortenx = 0f
        set(value) {
            field = value
            invalidate()
        }
    var sweepangle = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val ProgressPaint = Paint().apply {
        color = resources.getColor(R.color.colorBlue)
        style = Paint.Style.FILL
    }//设置矩形的画笔
    private val CirclePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }//设置圆弧的画笔
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(lshortenx,0f,
            width - rshortenx,height.toFloat(),
            dp2px(25),dp2px(25),ProgressPaint)
        canvas?.drawArc(
            (width-height)*0.5f + 0.2f*height,
            0.2f*height,
            (width-height)*0.5f + 0.8f*height,
            0.8f*height,
            -90f,sweepangle,false,CirclePaint
        )
    }
    fun Change_to_Circle(): ValueAnimator {
        val change_to_circle =  ValueAnimator.ofFloat(0f,(width-height)/2f).apply {
            duration = 800
            addUpdateListener {
                lshortenx = it.animatedValue as Float
                rshortenx = it.animatedValue as Float
                invalidate()
            }
        }
        return change_to_circle
    }
    fun drawCircle(): ValueAnimator {
        val drawcircle = ValueAnimator.ofFloat(-90f,360f).apply {
            duration = 800
            addUpdateListener {
                sweepangle = it.animatedValue as Float
                invalidate()
            }
        }
        return drawcircle
    }

    fun dp2px(dp:Int):Float = context.resources.displayMetrics.density*dp+0.5f
}