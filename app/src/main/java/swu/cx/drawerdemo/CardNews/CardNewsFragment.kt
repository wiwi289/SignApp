package swu.cx.drawerdemo.CardNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_card_news.*
import swu.cx.drawerdemo.Adapter.NewsPageAdapter
import swu.cx.drawerdemo.R
import androidx.appcompat.widget.Toolbar
import swu.cx.drawerdemo.MainActivity

class CardNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewpage.adapter = NewsPageAdapter(requireActivity())
        TabLayoutMediator(newsTab,viewpage){tab,position->
            when(position){
                0->tab.text="校内新闻"
                else->tab.text = "校内圈"
            }
        }.attach()

    }
}