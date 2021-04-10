package swu.cx.drawerdemo.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CircleOptions
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import swu.cx.drawerdemo.CardSignIn.MapDialog
import swu.cx.drawerdemo.R
import kotlin.math.pow

object GaoDeMap {
    private const val STD_LONGITUDE = 105.57941373816328
    private const val STD_LATITUDE = 29.395994259998517
    private const val RADIUS = 500.0
    private lateinit var aMap: AMap
    private lateinit var mLocationClient: AMapLocationClient
    private lateinit var option:AMapLocationClientOption
    private var hasCircle = false
    private val _location = MutableLiveData<String>()
    val location:LiveData<String>
        get() = _location
    private lateinit var mapDialog: MapDialog
    val isInCircle = MutableLiveData(true)
     @SuppressLint("InflateParams")
     fun initMap(savedInstanceState: Bundle?, context: Context){
        val view = LayoutInflater.from(context).inflate(R.layout.define_map_dialog_layout,null,false)
         mapDialog = MapDialog(view)
        val mapView = view.findViewById<MapView>(R.id.mapView)
         mapView.onCreate(savedInstanceState)
         aMap = mapView.map
        option = AMapLocationClientOption()
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationClient = AMapLocationClient(context)
        mLocationClient.setLocationOption(option)
         aMap.addCircle(CircleOptions().center(LatLng(STD_LATITUDE,STD_LONGITUDE)).radius(RADIUS).strokeWidth(3.0f))
        mLocationClient.setLocationListener { location->
            _location.value = location.address
            val longitudeDiffer = location.longitude- STD_LONGITUDE
            val latitudeDiffer = location.latitude- STD_LATITUDE
            if (longitudeDiffer.pow(2) +latitudeDiffer.pow(2)>RADIUS.pow(2)){
                isInCircle.value = false
            }
        }
        mLocationClient.startLocation()
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000)
        aMap.myLocationStyle = myLocationStyle
        aMap.isMyLocationEnabled = true
         aMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }
    fun showMap(){
       mapDialog.show()
    }
}