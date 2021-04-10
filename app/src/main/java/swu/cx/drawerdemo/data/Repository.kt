package swu.cx.drawerdemo.data

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.os.Message
import swu.cx.drawerdemo.CardSignIn.AllLeaveInforFragment
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.TeacherDao.showLeaveFragment
import swu.cx.drawerdemo.TeacherDao.showSignFragment
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Welcome.LoadAnimationFragment
import swu.cx.drawerdemo.WelcomeActivity
import swu.cx.drawerdemo.data.InforModel.AttendanceInfo
import swu.cx.drawerdemo.data.InforModel.LeaveInfor
import swu.cx.drawerdemo.data.InforModel.Student
import swu.cx.drawerdemo.data.InforModel.releasedAttendanceTask

class Repository private constructor(){
    var sID:String?=null
    var studentInfor: Student?=null
    var studentLeaveInfors:Array<LeaveInfor>?=null
    var orgAttandenceInfors:Array<AttendanceInfo>?=null
    var noSignStudentLists :Array<AttendanceInfo>?=null
    var allStudentLeaves:Array<LeaveInfor>?=null
    var releasedAttendancetask:Array<releasedAttendanceTask>?=null
    val handler = @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                MSG_TYPE_STUDENTINFOR -> studentInfor= msg.obj as Student
                MSG_TYPE_SIGNTASKS ->{
                    orgAttandenceInfors = msg.obj as Array<AttendanceInfo>
                    convertAttandenceInfors()
                }
                MSG_TYPE_LEAVEINFORS ->studentLeaveInfors = msg.obj as Array<LeaveInfor>
                MSG_TYPE_UPDATE_LEAVINFORS ->{
                    studentLeaveInfors = msg.obj as Array<LeaveInfor>
                    AllLeaveInforFragment.leaveAdapter.notifyDataSetChanged()
                }
                MSG_TYPE_UPDATE_ALL_LEAVES->{
                    allStudentLeaves = msg.obj as Array<LeaveInfor>
                    showLeaveFragment.allLeaveAdapter.notifyDataSetChanged()
                }
                MSG_TYPE_NO_SIGNS_LIST ->{
                    noSignStudentLists = msg.obj as Array<AttendanceInfo>
                    LoadAnimationFragment.delayAnim.start()
                    WelcomeActivity.isRestart = false
                }
                MSG_TYPE_UPDATE_NOSIGN_LISTS->{
                    noSignStudentLists = msg.obj as Array<AttendanceInfo>
                    showSignFragment.showSignAdapter.notifyDataSetChanged()
                }
                MSG_TYPE_ALL_LEAVES ->allStudentLeaves = msg.obj as Array<LeaveInfor>
                MSG_TYPE_RELEASEATTENDANCE_TASK -> {
                        releasedAttendancetask = msg.obj as Array<releasedAttendanceTask>
                        NetWorkDao.loadNoSignStudents(this)
                }
            }
        }
    }
    var signdatas:MutableList<signData>?=null
    val friendsCircledatas:MutableList<friendsCircleData> = mutableListOf()
    val schoolNewsdatas:MutableList<schoolNewsData> = mutableListOf()
    val scoolNewsImg:Array<Array<Int>> = arrayOf(
        arrayOf(),
        arrayOf(),
        arrayOf(R.drawable.news3),
        arrayOf(R.drawable.news41, R.drawable.news42, R.drawable.news43, R.drawable.news44),
        arrayOf()
    )
    val scoolNewsContent:Array<Int> = arrayOf(
        R.string.news1,
        R.string.news2,
        R.string.news3,
        R.string.news4,
        R.string.news5
    )
    val friendsCircleImgs:Array<Array<Int>> = arrayOf(
        arrayOf(R.drawable.stu1),
        arrayOf(),
        arrayOf(R.drawable.stu3),
        arrayOf(R.drawable.stu4),
        arrayOf(R.drawable.stu51, R.drawable.stu52),
        arrayOf(R.drawable.stu6),
        arrayOf(R.drawable.stu71, R.drawable.stu72),
        arrayOf(R.drawable.stu8),
        arrayOf(R.drawable.stu9),
        arrayOf(R.drawable.stu10),
        arrayOf(R.drawable.stu11),
        arrayOf(R.drawable.stu12),
        arrayOf(R.drawable.stu131, R.drawable.stu132),
        arrayOf(R.drawable.stu141, R.drawable.stu142),
    )
    val frinendsCircleHeadIcon:Array<Int> = arrayOf(
        R.drawable.head1,
        R.drawable.head2,
        R.drawable.head3,
        R.drawable.head4,
        R.drawable.head5,
        R.drawable.head6,
        R.drawable.head7,
        R.drawable.head8,
        R.drawable.head9,
        R.drawable.head10,
        R.drawable.head11,
        R.drawable.head12,
        R.drawable.head13,
        R.drawable.head14
    )
    val friendsCircleComments:Array<Int> = arrayOf(
        R.string.stu1,
        R.string.stu2,
        R.string.stu3,
        R.string.stu4,
        R.string.stu5,
        R.string.stu6,
        R.string.stu7,
        R.string.stu8,
        R.string.stu9,
        R.string.stu10,
        R.string.stu11,
        R.string.stu12,
        R.string.stu13,
        R.string.stu14
    )
    init {
        initFriendsCircledatas()
        initSchoolNewsdatas()
    }
    companion object{
        const val MSG_TYPE_STUDENTINFOR = 1
        const val MSG_TYPE_SIGNTASKS = 2
        const val MSG_TYPE_LEAVEINFORS = 3
        const val MSG_TYPE_UPDATE_LEAVINFORS = 4
        const val MSG_TYPE_NO_SIGNS_LIST = 5
        const val MSG_TYPE_ALL_LEAVES = 6
        const val MSG_TYPE_RELEASEATTENDANCE_TASK = 7
        val MSG_TYPE_UPDATE_NOSIGN_LISTS = 8
        val MSG_TYPE_UPDATE_ALL_LEAVES = 9
        val instance: Repository by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            Repository()
        }
        fun dpTopx(dp:Int,resources:Resources)= (resources.displayMetrics.density*dp).toInt()
    }
    fun loadAllStudentLeaves(){
        NetWorkDao.loadAllStudentLeaves(handler)
    }
    fun loadAllReleasedTasks(){
        NetWorkDao.loadReleasedTasks(handler)
    }
    fun LoadStudent(){
        NetWorkDao.loadStudent(sID!!, handler)
    }
    fun LoadAllLeaveInfors(){
        NetWorkDao.getAllLeaveInfor(sID!!,handler)
    }
    fun UpDateAllLeaveInfors(){
        NetWorkDao.upDateLeaveInfor(sID!!,handler)
    }
    fun convertAttandenceInfors(){
        signdatas = mutableListOf()
        orgAttandenceInfors?.forEach {
            if (it.kqstate=="未打卡") {
                signdatas!!.add(signData("未打卡 >", Color.parseColor("#000000"), true))
            }else{
                signdatas?.add(signData("已打卡 >", Color.parseColor("#d9d9d9"),false))
            }
        }
    }
    fun loadAllSignTasks(){
        NetWorkDao.studentSignTasks(sID!!,handler)
    }
    fun initFriendsCircledatas(){
        for (i in friendsCircleImgs.indices){
            friendsCircledatas.add(friendsCircleData(frinendsCircleHeadIcon[i],friendsCircleImgs[i],friendsCircleComments[i]))
        }
    }
    fun initSchoolNewsdatas(){
        for (i in scoolNewsContent.indices){
            schoolNewsdatas.add(schoolNewsData(scoolNewsContent[i],scoolNewsImg[i]))
        }
    }
}