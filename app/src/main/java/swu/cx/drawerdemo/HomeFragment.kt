package swu.cx.drawerdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.GoalRow
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import swu.cx.drawerdemo.Utils.GaoDeMap
import swu.cx.drawerdemo.Utils.SystemUtil


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        GaoDeMap.initMap(savedInstanceState,requireContext())
        GaoDeMap.location.observe(requireActivity()){
            signInfor.text = it
        }
        todayText.text = SystemUtil.getNowDate()
        news.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_cardNewsFragment)
        }
        signIn.setOnClickListener {
          val action = HomeFragmentDirections.actionHomeFragmentToCardSignFragment()
            findNavController().navigate(action)
        }
        Sgrade.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_gradeFragment)
        }
        course.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_courseFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        signInfor.text = GaoDeMap.location.value
        (requireActivity() as MainActivity).toolbar.setNavigationIcon(R.drawable.ic_person)
    }

}