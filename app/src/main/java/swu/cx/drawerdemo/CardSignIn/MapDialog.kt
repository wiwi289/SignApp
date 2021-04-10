package swu.cx.drawerdemo.CardSignIn

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.Utils.GaoDeMap

class MapDialog(private val view: View): Dialog(view.context, R.style.MapDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(view)
            setCancelable(false)
            val lp = window?.attributes
            lp?.width = dpTopx(300)
            lp?.height = dpTopx(440)
            window?.attributes = lp

            val cancelBtn = view.findViewById<ImageButton>(R.id.cancelBtn)
            cancelBtn.setOnClickListener {
                hide()
            }
    }
    private fun dpTopx(dp:Int) = (context.resources.displayMetrics.density*dp).toInt()
}