package swu.cx.drawerdemo.Welcome

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class waitLoading:View {
    //记录半径
    private var radius=0f
    //记录中心点坐标
    private var cx=0f
    private var cy=0f

    //画笔
    private val mpaint=Paint().apply {
        color=Color.MAGENTA
        style=Paint.Style.FILL
    }

    //记录延迟时间
    private val Delays= arrayOf(120L,240L,360L)
    //记录动画
    private val anims= mutableListOf<ValueAnimator>()
    //记录缩放程度
    private var scales= mutableListOf(1f,1f,1f)
    private val animatorset=AnimatorSet()
    constructor(context:Context):super(context){}
    constructor(context: Context,attri:AttributeSet):super(context,attri){}
    constructor(context: Context,attri: AttributeSet,style:Int):super(context,attri,style){}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (width>=height){
            radius=height/2f
            if (7*radius>width){
                radius=width/7f
            }
        }else{
            radius=width/7f
        }
        cx=(width-7*radius)/2f+radius
        cy=height/2f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(cx,cy,radius*scales[0],mpaint)
        canvas?.drawCircle(cx+2.5f*radius,cy,radius*scales[1],mpaint)
        canvas?.drawCircle(cx+5f*radius,cy,radius*scales[2],mpaint)
    }
    private fun createAnim(){
        for (index in 0..2){
            ValueAnimator.ofFloat(1.0f,0.3f,1.0f).apply {
                duration=500
                repeatCount=ValueAnimator.INFINITE
                startDelay=Delays[index]
                addUpdateListener {
                    scales[index]=it.animatedValue as Float
                    invalidate()
                }
                anims.add(this)
            }
        }
        for (item in anims){
            animatorset.play(item)
        }
    }
    fun startAnim(){
        createAnim()
        if (animatorset.isPaused){
            animatorset.resume()
        }else{
            animatorset.start()
        }
    }
    fun stopAnim(){
        animatorset.pause()
    }
}