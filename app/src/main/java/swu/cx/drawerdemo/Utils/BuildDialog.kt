@file:Suppress("DEPRECATION")

package swu.cx.drawerdemo.Utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import swu.cx.drawerdemo.Adapter.signAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

object BuildDialog {
    val START_DAY = 1
    val END_DAY = 2
      lateinit var startDay:Date
      lateinit var endDay:Date
    val calender = Calendar.getInstance()
    fun BuildDateDialog(activity: Activity, showText: TextView,type:Int){
        var nowday = if (type == START_DAY) calender.get(Calendar.DAY_OF_MONTH) else (calender.get(Calendar.DAY_OF_MONTH)+1)
        Log.v("cx","日期：$nowday")
        DatePickerDialog(
            activity,
            { view, year, month, dayOfMonth ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, month)
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val format = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(format, Locale.CHINA)
                val time = sdf.format(calender.time)
                showText.text = time
                when(type){
                    START_DAY-> startDay = calender.time
                    else-> endDay = calender.time
                }
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            nowday
        ).show()
    }
    fun BuildTimeDialog(activity: Activity, showText: TextView){
        TimePickerDialog(activity,
            { view, hour, minute ->
                val date = String.format("%d:%d", hour, minute)
                showText.text = date
            }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true
        ).show()
    }
    fun BuildSignDialog(context: Context, activity:Activity, adpter: signAdapter, position:Int){
        var temperature = ""
        Dialog(context, R.style.mydialog).apply {
            val signBoard = LayoutInflater.from(activity).inflate(R.layout.sign_board_layout, null)
            val group = signBoard.findViewById<RadioGroup>(R.id.group)
            val cancel = signBoard.findViewById<Button>(R.id.cancelBtn)
            val sign = signBoard.findViewById<Button>(R.id.signBtn)

            cancel.setOnClickListener {
                this.dismiss()
                Toast.makeText(activity, "您取消了打卡", Toast.LENGTH_SHORT).show()
            }
            sign.setOnClickListener {
                when(group.checkedRadioButtonId){
                    R.id.check1->{
                        Repository.instance.signdatas!![position].stateText = "已打卡 >"
                        Repository.instance.signdatas!![position].color = R.color.gray
                        temperature = "正常"
                    }
                    else-> {
                        Repository.instance.signdatas!![position].stateText = "体温偏高！"
                        temperature = "体温偏高"
                    }
                }
                Repository.instance.signdatas!![position].isClickable = false
                adpter.notifyDataSetChanged()
                Toast.makeText(context, "您已打卡", Toast.LENGTH_SHORT).show()
                val attendanceInfo = Repository.instance.orgAttandenceInfors!![position]
                attendanceInfo.apply {
                    kqstate = "已打卡"
                    kqlocation = GaoDeMap.location.value!!
                    kqtime = SystemUtil.getCurrentTime()
                    kqtemperature = temperature
                }
                NetWorkDao.upDateStudentSign(attendanceInfo)
                this.dismiss()
            }
            setContentView(signBoard)
            show()
        }
    }
}