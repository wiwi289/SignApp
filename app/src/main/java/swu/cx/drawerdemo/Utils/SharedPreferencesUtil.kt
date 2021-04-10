package swu.cx.drawerdemo.Utils

import android.content.Context

class SharedPreferencesUtil private constructor(){
    private val USER_INFOR = "user_infor"
    companion object{
        private lateinit var mContext:Context
        val Key = "user_id"
        var instance:SharedPreferencesUtil ?= null
        fun getInstance(context: Context):SharedPreferencesUtil{
            mContext = context
            if (instance==null){
                synchronized(this){
                    if (instance==null){
                        instance = SharedPreferencesUtil()
                    }
                }
            }
            return instance!!
        }
    }
    fun saveUserId(userId:String){
        mContext.getSharedPreferences(USER_INFOR,Context.MODE_PRIVATE).edit().apply {
            putString(Key,userId)
            apply()
        }
    }
    fun getUserId()= mContext.getSharedPreferences(USER_INFOR,Context.MODE_PRIVATE).getString(Key,null)
}