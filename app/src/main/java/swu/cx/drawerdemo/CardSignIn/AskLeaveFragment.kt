package swu.cx.drawerdemo.CardSignIn

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ask_leave.*
import swu.cx.drawerdemo.MainActivity
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Utils.BuildDialog
import swu.cx.drawerdemo.Utils.GaoDeMap
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Utils.SystemUtil
import swu.cx.drawerdemo.data.InforModel.LeaveInfor


class AskLeaveFragment : Fragment() {
    val hanlder = @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                TYPE_SUBMIT_LEAVE_INFOR->{
                    if ((msg.obj as String)=="success"){
                        Repository.instance.LoadAllLeaveInfors()
                        Toast.makeText(requireContext(),"提交成功",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(),"提交失败",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    companion object{
        val TYPE_SUBMIT_LEAVE_INFOR = 1
    }
    var instanceState:Bundle?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ask_leave, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        instanceState = savedInstanceState
        super.onActivityCreated(savedInstanceState)
        chooseStartDay.setOnClickListener {
            BuildDialog.BuildDateDialog(requireActivity(), it as TextView,BuildDialog.START_DAY)
        }
        chooseStartTime.setOnClickListener {
            BuildDialog.BuildTimeDialog(requireActivity(), it as TextView)
        }
        chooseEndDay.setOnClickListener {
            BuildDialog.BuildDateDialog(requireActivity(), it as TextView,BuildDialog.END_DAY)
        }
        chooseEndTime.setOnClickListener {
            BuildDialog.BuildTimeDialog(requireActivity(), it as TextView)
        }
        submit.setOnClickListener {
            val isInValid = chooseStartDay.text.isEmpty()||chooseEndDay.text.isEmpty()||chooseStartTime.text.isEmpty()||chooseEndTime.text.isEmpty()||reason.text.isEmpty()
            if (isInValid){
                Toast.makeText(requireContext(),"请假信息不完整!!",Toast.LENGTH_SHORT).show()
            }else{
                submitLeaveInfor()
                clearUI()
            }

        }
        setLeaveBoardInfor()
    }

    override fun onResume() {
        super.onResume()
        location.text = GaoDeMap.location.value
    }
    @SuppressLint("SetTextI18n")
    fun setLeaveBoardInfor(){
        leaveName.text = Repository.instance.studentInfor!!.name
        leaveClass.text = Repository.instance.studentInfor!!.grade+ Repository.instance.studentInfor!!.eclass
        leaveDepart.text = Repository.instance.studentInfor!!.college
    }
    fun clearUI(){
        chooseStartDay.text = "选择日期"
        chooseEndDay.text = "选择日期"
        chooseStartTime.text = "选择时间"
        chooseEndTime.text = "选择时间"
        reason.setText("")
    }
    fun submitLeaveInfor(){
        val days = ((BuildDialog.endDay.time-BuildDialog.startDay.time)/(24*60*60*1000)).toInt()
        if (days<=0){
            (requireActivity() as MainActivity).showMessage("日期选择不合法，请重新选择!!")
            clearUI()
            return
        }
        val leave = LeaveInfor(
            " " ,
            Repository.instance.studentInfor!!.studentid,
            Repository.instance.studentInfor!!.name,
            days.toString(),
            "",
            "",
            reason.text.toString(),
            "110",
            "待审批",
            "${chooseEndDay.text} ${chooseEndTime.text}",
            SystemUtil.getCurrentTime(),
            "${chooseStartDay.text} ${chooseStartTime.text}",
            "110",
            location.text.toString(),
        )
        NetWorkDao.submitAskLeaveInfo(leave,hanlder)
    }
}