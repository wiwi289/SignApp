package swu.cx.drawerdemo.TeacherDao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_show_leave.*
import kotlinx.android.synthetic.main.fragment_show_sign.*
import swu.cx.drawerdemo.Adapter.showAllLeaveInforAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.Utils.SignItemDecoration
import swu.cx.drawerdemo.data.Repository


class showLeaveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_leave, container, false)
    }
    companion object{
        val allLeaveAdapter = showAllLeaveInforAdapter()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        allLeaveRecycler.layoutManager = LinearLayoutManager(
            requireContext(),LinearLayoutManager.VERTICAL,false
        )
        allLeaveRecycler.adapter = allLeaveAdapter
        allLeaveRecycler.addItemDecoration(SignItemDecoration(100, 100, 100, 0))
        allLeavesRefresh.setOnRefreshListener {
            Repository.instance.loadAllStudentLeaves()
            Thread{
                Thread.sleep(1000)
                allLeavesRefresh.isRefreshing = false
            }.start()
        }
    }
}