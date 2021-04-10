package swu.cx.drawerdemo.Welcome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import kotlinx.android.synthetic.main.fragment_register.*
import swu.cx.drawerdemo.R

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getcode.setOnClickListener {
            BmobSMS.requestSMSCode(phonenumber.text.toString(), "cx", object : QueryListener<Int>() {
                override fun done(smsId: Int?, e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(requireContext(), "验证码发送成功", Toast.LENGTH_LONG).show()
                    } else {
                        Log.v("错误代码", e.errorCode.toString())
                        Toast.makeText(requireContext(), "验证码发送失败", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }//发送短信
        nextbtn.setOnClickListener {
            if (setpassword.text.isEmpty()) {
                Toast.makeText(requireContext(), "密码不能为空", Toast.LENGTH_LONG).show()
            } else {
                BmobSMS.verifySmsCode(
                    phonenumber.text.toString(),
                    verifycode.text.toString(),
                    object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            if (e == null) {
                                val action =
                                    RegisterFragmentDirections.actionRegisterFragmentToPersonInforFragment(
                                        setpassword.text.toString(),
                                        phonenumber.text.toString()
                                    )
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(requireContext(), "验证码错误", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }//验证短信
        }
    }
}