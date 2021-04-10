package swu.cx.drawerdemo.TeacherDao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_teacher.*
import swu.cx.drawerdemo.Adapter.TeacherPageAdapter
import swu.cx.drawerdemo.R

class TeacherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        teacherPage.adapter = TeacherPageAdapter(requireActivity())
        TabLayoutMediator(teacherTab,teacherPage){tab,position->
            when(position){
                0->tab.text="未打卡名单"
                else-> tab.text = "请假名单"
            }
        }.attach()
        tExchange.setOnClickListener {
            val action = TeacherFragmentDirections.actionTeacherFragmentToChooseIDFragment()
            findNavController().navigate(action)
            for (i in 0 until requireFragmentManager().backStackEntryCount){
                requireFragmentManager().popBackStack()
            }
        }
    }
}