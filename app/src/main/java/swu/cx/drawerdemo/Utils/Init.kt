package swu.cx.drawerdemo.Utils

import android.app.Activity
import android.app.Application
import cn.bmob.v3.Bmob
import cn.jpush.android.api.JPushInterface

class Init:Application() {
    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(this, "d638a1e12b050e32056d0c3c18643c2c")
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}