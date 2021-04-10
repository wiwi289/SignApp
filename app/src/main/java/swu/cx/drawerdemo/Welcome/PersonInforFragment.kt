package swu.cx.drawerdemo.Welcome

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_person_infor.*
import kotlinx.android.synthetic.main.fragment_person_infor.Sgrade
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Utils.OssUpload
import swu.cx.drawerdemo.Utils.SharedPreferencesUtil
import swu.cx.drawerdemo.data.InforModel.Student
import java.io.File
import java.io.FileOutputStream

class PersonInforFragment : Fragment() {
    private val args:PersonInforFragmentArgs by navArgs()
    private var picPath:String?=null
    val handler = @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            uploadProgress.visibility = View.GONE
            when(msg.what){
                UPLOAD_USER_ICON->{
                    //停止加载动画
                    Toast.makeText(requireContext(),"注册成功",Toast.LENGTH_SHORT).show()
                    val action = PersonInforFragmentDirections.actionPersonInforFragmentToLoginFragment("学生",Snum.text.toString(),args.Spwd)
                    SharedPreferencesUtil.getInstance(requireActivity().applicationContext).saveUserId(Snum.text.toString())
                    findNavController().navigate(action)
                }
                else->{
                    val result = msg.obj as String
                    if (result=="success"){
                        OssUpload.doUpload(requireContext(),"user_icon/${Snum.text}.png",picPath!!,this,OssUpload.UPLOAD_TYPE_USER_ICON)
                    }else{
                        Toast.makeText(requireContext(),"学号已存在，请重新输入！！",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
    companion object{
        val UPLOAD_USER_ICON = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_infor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        headImage.setOnClickListener {
            Intent().apply {
                action=Intent.ACTION_PICK
                type="image/*"
                startActivityForResult(this,20)
            }
        }
        submitInfor.setOnClickListener {
            uploadProgress.visibility = View.VISIBLE
            Thread{
                Thread.sleep(2000)
            }.start()
            val Sid = Snum.text.toString()
            val grade = Sgrade.text.toString()
            val eclass = Class.text.toString()
            val department = department.text.toString()
            val major = major.text.toString()
            val name = name.text.toString()
            val student = Student(department,Sid,major,grade,name,eclass,args.Spwd,args.phone)
            NetWorkDao.submitStudentInfor(student,handler)
            NetWorkDao.addOneFakeAttendenceInfor(Sid,name)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode!=Activity.RESULT_CANCELED) {
            data?.data.let {
                requireActivity().contentResolver.openInputStream(it!!).use {inputStream->
                    BitmapFactory.decodeStream(inputStream).also {img->
                        headImage.setImageBitmap(img)
                        val file = File(requireActivity().filesDir,"${Snum.text}.png")
                        picPath = file.absolutePath
                        FileOutputStream(file).also {fos->
                            img.compress(Bitmap.CompressFormat.JPEG,50,fos)
                        }
                    }
                }
            }
        }
    }
}