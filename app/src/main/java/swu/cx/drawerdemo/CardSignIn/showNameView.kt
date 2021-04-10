package swu.cx.drawerdemo.CardSignIn

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import swu.cx.drawerdemo.R

class showNameView: View {
    var text = ""
    private var radius = 0f
    private var cx = 0f
    private var cy = 0f
    private var mTextSize = 0f
        set(value) {
            field = value
            textPaint.textSize = value
        }
    private var mBgColor = 0
        set(value) {
            field = value
            paint.color = value
        }

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = mBgColor
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL
        textSize = mTextSize
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
    }
    @SuppressLint("ResourceAsColor")
    constructor(context:Context, attri: AttributeSet):super(context,attri){
           val typedArray =  context.obtainStyledAttributes(attri,R.styleable.showNameView)
            mTextSize = typedArray.getFloat(R.styleable.showNameView_TextSize,0f)
        mBgColor = typedArray.getColor(R.styleable.showNameView_bgColor,R.color.bule)
            typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = width/2f
         cx = width/2f
        cy = height/2f
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(cx,cy,radius,paint)
        val mtrics = textPaint.fontMetrics
        val space = (mtrics.descent-mtrics.ascent)/2f-mtrics.descent
        canvas?.drawText(text,cx,cy+space,textPaint)
    }


}