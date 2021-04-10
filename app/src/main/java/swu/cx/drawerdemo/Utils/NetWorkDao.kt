package swu.cx.drawerdemo.Utils

import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import swu.cx.drawerdemo.CardSignIn.AllLeaveInforFragment
import swu.cx.drawerdemo.CardSignIn.AskLeaveFragment
import swu.cx.drawerdemo.CardSignIn.SignInFragment
import swu.cx.drawerdemo.MainActivity
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Welcome.LoginFragment
import swu.cx.drawerdemo.WelcomeActivity
import swu.cx.drawerdemo.data.InforModel.Student
import swu.cx.drawerdemo.data.InforModel.AttendanceInfo
import swu.cx.drawerdemo.data.InforModel.LeaveInfor
import swu.cx.drawerdemo.data.InforModel.releasedAttendanceTask
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetWorkDao {
    private const val IP = "10.129.80.46"
    private val gson = Gson()
    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()
    var welcomeActivity:WelcomeActivity?=null
    var mainActivity:MainActivity?=null
    fun submitStudentInfor(s: Student,handler: Handler){
        Thread {
            val requestBody = FormBody.Builder()
                .add("studentid", s.studentid)
                .add("studentpassword", s.studentpassword)
                .add("grade", s.grade)
                .add("eclass", s.eclass)
                .add("college", s.college)
                .add("major", s.major)
                .add("name", s.name)
                .add("studenttell", s.studenttell)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/student/add")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("网络超时，请稍后再试！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val msg = Message()
                    msg.obj = result
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    //查询用户是否存在
    fun searchStudent(id:String, handler: Handler){
        Thread {
            val requestBody = FormBody.Builder()
                .add("studentid", id)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/student/querybystudentexactly")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    welcomeActivity?.runOnUiThread {
                        welcomeActivity?.showMessage("连接超时！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val msg = Message()
                    msg.what = LoginFragment.SEARCH_STUDENT
                    msg.obj = result
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun searchTeacher(id:String, handler: Handler){
        Thread {
            val requestBody = FormBody.Builder()
                .add("teacher", id)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/teacher/querybyteacherexactly")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    welcomeActivity?.runOnUiThread {
                        welcomeActivity?.showMessage("连接超时！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val msg = Message()
                    msg.what = LoginFragment.SEARCH_TEACHER
                    msg.obj = result
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    //登录判断
    fun loginStudent(id:String, pwd:String, handler: Handler){
        Thread {
            val requestBody = FormBody.Builder()
                .add("studentid", id)
                .add("studentpassword", pwd)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/student/querybyidandpwd")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    welcomeActivity?.runOnUiThread {
                        welcomeActivity?.showMessage("连接超时，请检查网络连接！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val msg = Message()
                    msg.what = LoginFragment.LOGIN_STUDENT
                    msg.obj = result
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun loginTeacher(id:String, pwd:String, handler: Handler){
        Thread {
            val requestBody = FormBody.Builder()
                .add("teacher", id)
                .add("password", pwd)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/teacher/querybyteacheridandpwd")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    welcomeActivity?.runOnUiThread {
                        welcomeActivity?.showMessage("连接超时，请检查网络连接！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val msg = Message()
                    msg.what = LoginFragment.LOGIN_STUDENT
                    msg.obj = result
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun loadStudent(id:String,handler: Handler){
        Thread {
            val request = Request.Builder()
                .url("http://$IP:8080/student/querybystudentid?studentid=$id")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("网络连接超时！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val student = gson.fromJson(result, Array<Student>::class.java)
                    val msg = Message()
                    msg.what = Repository.MSG_TYPE_STUDENTINFOR
                    msg.obj = student[0]
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun studentSignTasks(id: String,handler: Handler){
        Thread {
            val request = Request.Builder()
                .url("http://$IP:8080/attendance_info/querybystudent?student=$id")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val signTasks = gson.fromJson(result, Array<AttendanceInfo>::class.java)
                    val msg = Message()
                    msg.what = Repository.MSG_TYPE_SIGNTASKS
                    msg.obj = signTasks
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun submitAskLeaveInfo(leaveInfor: LeaveInfor,handler: Handler){
        Thread{
            val requestBody = FormBody.Builder()
                .add("student",leaveInfor.student)
                .add("qjtimestart",leaveInfor.qjtimestart)
                .add("qjtimeend",leaveInfor.qjtimeend)
                .add("qjtimechange",leaveInfor.qjtimechange)
                .add("qjday",leaveInfor.qjday)
                .add("location",leaveInfor.location)
                .add("qjcause",leaveInfor.qjcause)
                .add("fdy",leaveInfor.fdy)
                .add("reviewtime",leaveInfor.reviewtime)
                .add("xj",leaveInfor.xj)
                .add("xjtime",leaveInfor.xjtime)
                .add("qjstate",leaveInfor.qjstate)
                .add("studentname",leaveInfor.studentname)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/stuleave/add")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val msg = Message()
                    msg.what = AskLeaveFragment.TYPE_SUBMIT_LEAVE_INFOR
                    msg.obj = result
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun getAllLeaveInfor(id:String,handler: Handler){
        Thread {
            val request = Request.Builder()
                .url("http://$IP:8080/stuleave/querybystudent?student=$id")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val allLeaves = gson.fromJson(result, Array<LeaveInfor>::class.java)
                    val msg = Message()
                    msg.what = Repository.MSG_TYPE_LEAVEINFORS
                    msg.obj = allLeaves
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun upDateLeaveInfor(id:String,handler: Handler){
        Thread {
            val request = Request.Builder()
                .url("http://$IP:8080/stuleave/querybystudent?student=$id")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val allLeaves = gson.fromJson(result, Array<LeaveInfor>::class.java)
                    val msg = Message()
                    msg.what = Repository.MSG_TYPE_UPDATE_LEAVINFORS
                    msg.obj = allLeaves
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun upDateStudentSign(signInfor:AttendanceInfo){
        Thread{
            val requestBody =FormBody.Builder()
                .add("kqtime",signInfor.kqtime)
                .add("kqlocation",signInfor.kqlocation)
                .add("kqstate",signInfor.kqstate)
                .add("kqtemperature",signInfor.kqtemperature)
                .add("student",signInfor.student)
                .add("kqstarttime",signInfor.kqstarttime)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/attendance_info/querybystuandkst")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {

                }
            })
        }.start()
    }
    fun cancelLeaveAsk(id:String,time:String){
        Thread{
            val request = Request.Builder()
                .url("http://$IP:8080/stuleave/delbystudentandqjtimestart?student=$id&qjtimestart=$time")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {

                }
            })
        }.start()
    }
    fun loadNoSignStudents(handler: Handler){
        val size = Repository.instance.releasedAttendancetask!!.size
        val latestSignTask = Repository.instance.releasedAttendancetask!![size-1]
            Thread{
                val request = Request.Builder()
                    .url("http://$IP:8080/attendance_info/querynotdakastudents?kqstate=未打卡&kqstarttime=${latestSignTask.timestart}")
                    .build()
                val call = client.newCall(request)
                call.enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        mainActivity?.runOnUiThread {
                            mainActivity?.showMessage("加载超时，请稍后再试！！")
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val result = response.body?.string()
                        val noSignLists = gson.fromJson(result, Array<AttendanceInfo>::class.java)
                        val msg = Message()
                        msg.obj = noSignLists
                        msg.what = if (WelcomeActivity.isRestart) Repository.MSG_TYPE_NO_SIGNS_LIST else Repository.MSG_TYPE_UPDATE_NOSIGN_LISTS
                        handler.sendMessage(msg)
                    }
                })
            }.start()
    }
    fun loadReleasedTasks(handler: Handler){
        Thread{
            val request = Request.Builder()
                .url("http://$IP:8080/attendance/queryall")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    welcomeActivity?.runOnUiThread {
                        welcomeActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val allReleasedTasks = gson.fromJson(result,Array<releasedAttendanceTask>::class.java)
                    val msg = Message()
                    msg.what = Repository.MSG_TYPE_RELEASEATTENDANCE_TASK
                    msg.obj = allReleasedTasks
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun loadAllStudentLeaves(handler: Handler){
        Thread{
            val request = Request.Builder()
                .url("http://$IP:8080/stuleave/queryall")
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    welcomeActivity?.runOnUiThread {
                        welcomeActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()
                    val allLeaves = gson.fromJson(result, Array<LeaveInfor>::class.java)
                    val msg = Message()
                    msg.what = if (Repository.instance.allStudentLeaves==null) Repository.MSG_TYPE_ALL_LEAVES else Repository.MSG_TYPE_UPDATE_ALL_LEAVES
                    msg.obj = allLeaves
                    handler.sendMessage(msg)
                }
            })
        }.start()
    }
    fun changeLeaveState(leaveInfor: LeaveInfor,handler: Handler){
            Thread{
                val request = Request.Builder()
                    .url("http://$IP:8080/stuleave/updatestudentqjstate?qjstate=已销假&student=${leaveInfor.student}&qjtimestart=${leaveInfor.qjtimestart}")
                    .build()
                val call = client.newCall(request)
                call.enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        mainActivity?.runOnUiThread {
                            mainActivity?.showMessage("加载超时，请稍后再试！！")
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val msg = Message()
                        msg.what = AllLeaveInforFragment.MSG_REFRESH
                        handler.sendMessage(msg)
                    }
                })
            }.start()
    }
    fun addOneFakeAttendenceInfor(id:String,name:String){
        Thread {
            val requestBody = FormBody.Builder()
                .add("student", id)
                .add("kqtime", "000")
                .add("kqlocation", "000")
                .add("kqtype", "健康打卡")
                .add("kqstate", "已打卡")
                .add("kqtemperature", "000")
                .add("kqstarttime", "2020-12-10 07:00")
                .add("studentname", name)
                .build()
            val request = Request.Builder()
                .url("http://$IP:8080/attendance_info/add")
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    mainActivity?.runOnUiThread {
                        mainActivity?.showMessage("加载超时，请稍后再试！！")
                    }
                }
                override fun onResponse(call: Call, response: Response) {

                }
            })
        }.start()
    }

}