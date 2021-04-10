package swu.cx.drawerdemo.Welcome

import android.animation.Animator
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_login.*
import swu.cx.drawerdemo.MainActivity
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Utils.NetWorkDao
import swu.cx.drawerdemo.Utils.SharedPreferencesUtil
import swu.cx.drawerdemo.WelcomeActivity

class LoginFragment : Fragment(),Animator.AnimatorListener {
    val args:LoginFragmentArgs by navArgs()
    private val scale:Animator by lazy {
        ViewAnimationUtils.createCircularReveal(
            nowView,
            dp2px(200).toInt(),
            dp2px(780).toInt(),
            dp2px(25), 7000f
        ).apply {
            duration = 800
            addListener(this@LoginFragment)
        }
    }
    private lateinit var nowView:View
    val handler = @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val result = msg.obj as String
            when(msg.what){
                SEARCH_STUDENT -> {
                    if (result == "true") {
                        NetWorkDao.loginStudent(
                            username.text.toString(),
                            password.text.toString(),
                            this
                        )
                    } else {
                        Toast.makeText(requireContext(), "用户名不存在！！", Toast.LENGTH_SHORT).show()
                    }
                }
                LOGIN_STUDENT -> {
                    if (result != "密码错误") {
                        Repository.instance.sID = username.text.toString()
                        load.alpha = 0f
                        AnimatorSet().apply {
                            playSequentially(
                                progressBtn.Change_to_Circle(),
                                progressBtn.drawCircle(),
                                scale
                            )
                            start()
                        }
                    } else {
                        Toast.makeText(requireContext(), "密码错误！！", Toast.LENGTH_SHORT).show()
                    }
                }
                SEARCH_TEACHER -> {
                    if (result == "true") {
                        NetWorkDao.loginTeacher(
                            username.text.toString(),
                            password.text.toString(),
                            this
                        )
                    } else {
                        Toast.makeText(requireContext(), "用户名不存在！！", Toast.LENGTH_SHORT).show()
                    }
                }
                LOGIN_TEACHER -> {
                    if (result == "密码错误") {
                        Toast.makeText(requireContext(), "密码错误！！", Toast.LENGTH_SHORT).show()
                    } else {
                        load.alpha = 0f
                        AnimatorSet().apply {
                            playSequentially(
                                progressBtn.Change_to_Circle(),
                                progressBtn.drawCircle(),
                                scale
                            )
                            start()
                        }
                    }
                }
            }
        }
    }
    companion object{
        val SEARCH_STUDENT = 1
        val LOGIN_STUDENT = 2
        val SEARCH_TEACHER = 3
        val LOGIN_TEACHER = 4
    }
    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        WelcomeActivity.isClickable = true
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationStart(animation: Animator?) {
        WelcomeActivity.isClickable = false
        val handler = Handler()
        handler.postDelayed({
            if (args.UserType == "学生") {
                requireActivity().startActivityFromFragment(
                    this,
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    ).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK },
                    0
                )
            } else {
                val action = LoginFragmentDirections.actionLoadFragmentToTeacherFragment()
                findNavController().navigate(action)
            }
        }, 400)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nowView = inflater.inflate(R.layout.fragment_login, container, false)
        return nowView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        args.id?.let {

        }
        if (args.id==null){
            SharedPreferencesUtil.getInstance(requireActivity().applicationContext).getUserId()?.let {
                username.setText(it)
            }
        }else{
            username.setText(args.id)
        }
        args.pwd?.let {
            password.setText(it)
        }
        progressBtn.setOnClickListener {
            if(username.text.isNotEmpty()){
                if (password.text.isNotEmpty()) {
                    if (args.UserType=="老师"){
                        NetWorkDao.searchTeacher(username.text.toString(), handler)
                    }else{
                        NetWorkDao.searchStudent(username.text.toString(), handler)
                    }
                }else{
                    Toast.makeText(requireActivity(), "请输入密码！！", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireActivity(), "请输入账户名！！", Toast.LENGTH_SHORT).show()
            }
        }

        phone.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loadFragment_to_registerFragment)
        }
    }
    fun dp2px(dp: Int):Float = requireContext().resources.displayMetrics.density*dp+0.5f
}