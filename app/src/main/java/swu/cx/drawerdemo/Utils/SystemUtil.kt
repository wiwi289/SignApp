package swu.cx.drawerdemo.Utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

object SystemUtil {
    const val REQUEST_PERMISSIONS_CODE = 1
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )
    private val requiresPermissions = mutableListOf<String>()
    fun getCurrentTime():String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }
    fun getNowDate():String{
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }
    fun applyForJurisdictions(activity: Activity){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            permissions.forEach {
                if (ContextCompat.checkSelfPermission(activity,it)!=PackageManager.PERMISSION_GRANTED){
                        requiresPermissions.add(it)
                }
            }
        }
        if (requiresPermissions.size>0){
                activity.requestPermissions(requiresPermissions.toTypedArray(),
                    REQUEST_PERMISSIONS_CODE)
        }
    }
}