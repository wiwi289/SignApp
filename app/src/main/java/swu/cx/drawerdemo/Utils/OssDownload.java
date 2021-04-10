package swu.cx.drawerdemo.Utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OssDownload {
    //private static OSS oss = ;
    public static void  downLoadFile(String name, final Context context, final String localName, Handler handler) {

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI4G4qXpLtjQYXHxz8KngV","VRG5k3mEYjiJhKp6Bu0FBdMrLULIO3");
        ClientConfiguration conf = new ClientConfiguration();
        OSS oss = new OSSClient(context, "http://oss-cn-beijing.aliyuncs.com", credentialProvider, conf);

        // 构造下载文件请求。
        GetObjectRequest get = new GetObjectRequest("bucket-ljh", name);
        Log.v("cx","开始下载图片");
        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                InputStream inputStream = result.getObjectContent();
                byte[] buffer = new byte[2048];
                int len;
                File file=null;
                try {
                    file= new File(context.getExternalCacheDir(),localName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    while ((len = bis.read(buffer)) != -1) {
                        // 您可以在此处编写代码来处理下载的数据
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    handler.sendEmptyMessage(0);
                    fos.close();
                    bis.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            // GetObject请求成功，将返回GetObjectResult，其持有一个输入流的实例。返回的输入流，请自行处理。
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                Log.v("cx","下载失败了");
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }
}
