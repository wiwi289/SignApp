package swu.cx.drawerdemo.Welcome

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import swu.cx.drawerdemo.R

class LoadAnimationFragment : Fragment() {
    private lateinit var nowView: View
    companion object{
       lateinit var delayAnim:Animator
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        nowView = inflater.inflate(R.layout.fragment_load_animation, container, false)
        return nowView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        delayAnim = ObjectAnimator.ofFloat(0f,1f).apply {
            duration = 3000
            addListener(object :Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {

                    Navigation.findNavController(nowView).navigate(R.id.action_loadAnimationFragment2_to_chooseIDFragment)

                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
        }
    }

}