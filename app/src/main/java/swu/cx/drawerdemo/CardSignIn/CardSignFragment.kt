package swu.cx.drawerdemo.CardSignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_card_sign.*
import swu.cx.drawerdemo.Adapter.SignPageAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository


class CardSignFragment : Fragment() {
    init {
        Repository.instance.LoadAllLeaveInfors()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_sign, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signInPage.adapter = SignPageAdapter(requireActivity())
        TabLayoutMediator(tabLayout2,signInPage){tab,position->
            when(position){
                0->tab.text="健康打卡"
                1->tab.text = "请假管理"
                else-> tab.text = "历史假条"
            }
        }.attach()

    }
}