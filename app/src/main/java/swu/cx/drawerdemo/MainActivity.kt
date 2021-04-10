package swu.cx.drawerdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Utils.OssDownload
import swu.cx.drawerdemo.data.Repository
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var appConfigurationBar: AppBarConfiguration
    private lateinit var appController: NavController

    val handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(Is_Fisrt_Load) {
                waitLoading.stopAnim()
                waitLoading.visibility = View.INVISIBLE
                isClickable = true
                Is_Fisrt_Load = false
                val file = File(externalCacheDir,"/${Repository.instance.sID}.png")
                uri = Uri.fromFile(file)
                setDrawerInfor()
            }
        }
    }
    init {
        //开始下载动画
        if (Is_Fisrt_Load) {
            Repository.instance.loadAllSignTasks()
            Repository.instance.LoadStudent()
        }
    }
    companion object{
        var Is_Fisrt_Load = true
        var uri: Uri?=null
        var isClickable = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetWorkDao.mainActivity = this
        setSupportActionBar(toolbar)
        appController = findNavController(R.id.hostfragment)
        appConfigurationBar = AppBarConfiguration(setOf(R.id.homeFragment), drawLayout)
        setupActionBarWithNavController(appController, appConfigurationBar)
        if (Is_Fisrt_Load) {
            waitLoading.visibility = View.VISIBLE
            waitLoading.startAnim()
            Thread {
                OssDownload.downLoadFile(
                    "user_icon/${Repository.instance.sID}.png",
                    this,
                    "${Repository.instance.sID}.png",
                    handler
                )
            }.start()
        }
    }
    fun setDrawerInfor(){
        uri.let {
            headIcon.setImageURI(it)
        }
        sname.text = "姓名：${Repository.instance.studentInfor?.name}"
        sclass.text = "班级：${Repository.instance.studentInfor?.grade}${Repository.instance.studentInfor?.eclass}"
        smajor.text = "专业：${Repository.instance.studentInfor?.major}"
        sdepart.text = "院系：${Repository.instance.studentInfor?.college}"
    }
    override fun onSupportNavigateUp(): Boolean {
        return appController.navigateUp(appConfigurationBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.exchangeState) {
            WelcomeActivity.isRestart = true
            Intent(this, WelcomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun showMessage(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if(isClickable){
            super.dispatchTouchEvent(ev)
        }else{
            !isClickable
        }
    }
}