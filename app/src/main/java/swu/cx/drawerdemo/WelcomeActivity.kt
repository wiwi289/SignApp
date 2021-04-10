package swu.cx.drawerdemo

import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Utils.SystemUtil
import swu.cx.drawerdemo.data.Repository
import java.lang.Exception

class WelcomeActivity : AppCompatActivity() {
    init {
        Repository.instance.loadAllStudentLeaves()
        Repository.instance.loadAllReleasedTasks()
    }
    companion object{
        var isRestart = true
        var isClickable = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        NetWorkDao.welcomeActivity = this
        SystemUtil.applyForJurisdictions(this)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==SystemUtil.REQUEST_PERMISSIONS_CODE){
                grantResults.forEach {
                    if (it!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"请授予权限！",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setCancelable(false)
                .setMessage("为了您能够在后台收到打卡提醒，建议您允许本应用后台运行以及自启动")
                .setNegativeButton("拒绝"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .setPositiveButton("同意"){ dialogInterface: DialogInterface, i: Int ->
                    try {
                        startActivity(getAutoStartSettingIntent(this))
                    }catch(e: Exception) {
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }
                    dialogInterface.dismiss()
                }
                .create().show()
        }
    }
    private fun getAutoStartSettingIntent(context: Context): Intent? {
        var componentName: ComponentName? = null
        val brand = Build.MANUFACTURER
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        when (brand.toLowerCase()) {
            "huawei" -> componentName = ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")
            "xiaomi" -> componentName = ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
            "vivo" ->componentName =  ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
            "oppo" -> componentName = ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity")
            else -> {
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                intent.data = Uri.fromParts("package", context.packageName, null)
            }
        }
        intent.component = componentName
        return intent
    }
}