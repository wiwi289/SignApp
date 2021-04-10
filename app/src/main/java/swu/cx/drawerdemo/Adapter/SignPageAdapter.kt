package swu.cx.drawerdemo.Adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import swu.cx.drawerdemo.CardSignIn.AllLeaveInforFragment
import swu.cx.drawerdemo.CardSignIn.AskLeaveFragment
import swu.cx.drawerdemo.CardSignIn.SignInFragment

class SignPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int)=when(position){
        0-> SignInFragment()
        1-> AskLeaveFragment()
        else-> AllLeaveInforFragment()
    }
}