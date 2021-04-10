package swu.cx.drawerdemo.CardSignIn

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_all_leave_infor.*
import swu.cx.drawerdemo.Adapter.allLeaveInforAdapter
import swu.cx.drawerdemo.BuildConfig
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Utils.SignItemDecoration
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Utils.OssUpload
import java.io.File


class AllLeaveInforFragment : Fragment() {
    lateinit var uri: Uri
    private var position:Int?=null
    private var start_time:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_leave_infor, container, false)
    }
    val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
               MSG_TYPE_UPLOAD->{
                   progress.visibility = View.GONE
                   val leaveInfor = Repository.instance.studentLeaveInfors!![position!!]
                   NetWorkDao.changeLeaveState(leaveInfor,this)
               }
                MSG_REFRESH-> leaveAdapter.notifyDataSetChanged()
            }
        }
    }
    companion object{
         val leaveAdapter = allLeaveInforAdapter()
        val MSG_TYPE_UPLOAD = 1
        val MSG_REFRESH = 2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mLeaveRecycler.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )
        leaveAdapter.leaveCallBack={pos,startTime->
            position = pos
            start_time = startTime
            Intent().apply {
                action = "android.media.action.IMAGE_CAPTURE"
                val outputFile = File(requireActivity().externalCacheDir, "${Repository.instance.studentInfor?.name}")
                if (!outputFile.exists()) {
                    outputFile.createNewFile()
                }
                uri = FileProvider.getUriForFile(
                    requireActivity(),
                    BuildConfig.APPLICATION_ID + ".fileProvider",
                    outputFile
                )
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(this,0)
            }
        }
        mLeaveRecycler.adapter = leaveAdapter
        mLeaveRecycler.addItemDecoration(SignItemDecoration(100, 50, 100, 0))

        val refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {

            Thread{
                Thread.sleep(1000)
                leaveSwipeRefresh.isRefreshing = false
                Repository.instance.UpDateAllLeaveInfors()
            }.start()
        }
        leaveSwipeRefresh.setOnRefreshListener(refreshListener)
    }
    override fun onResume() {
        super.onResume()
       leaveAdapter.notifyDataSetChanged()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
                if (resultCode != Activity.RESULT_CANCELED) {
                    progress.visibility = View.VISIBLE
                    Thread {
                        OssUpload.doUpload(
                            requireContext(),
                            "Eliminate_holidays/${Repository.instance.studentInfor?.name}_$start_time.jpg",
                            requireActivity().externalCacheDir.toString() + "/${Repository.instance.studentInfor?.name}",
                            handler
                        ,OssUpload.UPLOAD_TYPE_CANCEL_LEAVE)
                    }.start()
                }
            }

}