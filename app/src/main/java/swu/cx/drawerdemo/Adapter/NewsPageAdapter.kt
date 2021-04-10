package swu.cx.drawerdemo.Adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import swu.cx.drawerdemo.CardNews.FriendsFragment
import swu.cx.drawerdemo.CardNews.NewsFragment

class NewsPageAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int)=when(position){
        0-> NewsFragment()
        else-> FriendsFragment()
    }
}