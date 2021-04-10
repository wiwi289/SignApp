package swu.cx.drawerdemo.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import swu.cx.drawerdemo.TeacherDao.showLeaveFragment
import swu.cx.drawerdemo.TeacherDao.showSignFragment

class TeacherPageAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount()=2

    override fun createFragment(position: Int)=when(position) {
        0->showSignFragment()
        else -> showLeaveFragment()
    }
}