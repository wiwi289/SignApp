package swu.cx.drawerdemo.CardCourse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.itheima.library.Info
import com.itheima.library.PhotoView
import kotlinx.android.synthetic.main.fragment_course.*
import swu.cx.drawerdemo.R

class CourseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoView: PhotoView = requireView().findViewById(R.id.lesson)
        photoView.enable()// 启用图片缩放功能
        val info: Info = PhotoView.getImageViewInfo(lesson)// 从普通的ImageView中获取Info
    }
}