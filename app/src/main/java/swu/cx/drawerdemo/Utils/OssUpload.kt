package swu.cx.drawerdemo.Utils

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import swu.cx.drawerdemo.CardSignIn.AllLeaveInforFragment
import swu.cx.drawerdemo.Welcome.PersonInforFragment

object OssUpload {
    val UPLOAD_TYPE_USER_ICON = 1
    val UPLOAD_TYPE_CANCEL_LEAVE = 2
    fun doUpload(context: Context,objectKey: String,uploadFilePath: String,handler: Handler,Type:Int){
        // ACCESS_ID,ACCESS_KEY是在阿里云申请的
        //以下信息绝密，不可外泄！！！！！
        val credentialProvider: OSSCredentialProvider = OSSPlainTextAKSKCredentialProvider("LTAI4G4qXpLtjQYXHxz8KngV","VRG5k3mEYjiJhKp6Bu0FBdMrLULIO3")
        val conf = ClientConfiguration()
        conf.connectionTimeout = 15 * 1000 // 连接超时，默认15秒
        conf.socketTimeout = 15 * 1000 // socket超时，默认15秒
        conf.maxConcurrentRequest = 8 // 最大并发请求数，默认5个
        conf.maxErrorRetry = 2 // 失败后最大重试次数，默认2次

        // OSS_ENDPOINT是一个OSS区域地址
        var oss = OSSClient(context, "http://oss-cn-beijing.aliyuncs.com", credentialProvider, conf)
        // 构造上传请求
        val put = PutObjectRequest("bucket-ljh", objectKey, uploadFilePath)
        try {
           oss.putObject(put)//进行文件上传
            val msg = Message()
            when(Type){
                UPLOAD_TYPE_USER_ICON->msg.what = PersonInforFragment.UPLOAD_USER_ICON
                UPLOAD_TYPE_CANCEL_LEAVE->msg.what = AllLeaveInforFragment.MSG_TYPE_UPLOAD
            }
            handler.sendMessage(msg)
        } catch (e: ClientException) {
            // 本地异常如网络异常等
            e.printStackTrace()
        } catch (e: ServiceException) {
            // 服务异常
            Log.e("RequestId", e.requestId)
            Log.e("ErrorCode", e.errorCode)
            Log.e("HostId", e.hostId)
            Log.e("RawMessage", e.rawMessage)
        }
    }

}