package swu.cx.drawerdemo.Welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_choose_i_d.*
import kotlinx.coroutines.delay
import swu.cx.drawerdemo.R

class ChooseIDFragment : Fragment() {
    var tWidth = 0
        set(value) {
            field = value
            val tx = ObjectAnimator.ofFloat(teacher,"translationX",-field.toFloat(),0f)
            val metrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
            val sx = ObjectAnimator.ofFloat(student,"translationX",metrics.widthPixels.toFloat(),0f)
            teacher.visibility = View.VISIBLE
            student.visibility = View.VISIBLE
            AnimatorSet().apply {
                playTogether(sx,tx)
                duration = 650
                start()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_i_d, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teacher.post {
            tWidth = teacher.width
        }
        teacher.setOnClickListener {
            val action = ChooseIDFragmentDirections.actionChooseIDFragmentToLoadFragment("老师",null,null)
            findNavController().navigate(action)
        }
        student.setOnClickListener {
            val action = ChooseIDFragmentDirections.actionChooseIDFragmentToLoadFragment("学生",null,null)
            findNavController().navigate(action)
        }
    }


}