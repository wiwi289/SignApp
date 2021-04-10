package swu.cx.drawerdemo.TeacherDao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_show_sign.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import swu.cx.drawerdemo.Adapter.showNoSignAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Utils.SignItemDecoration


class showSignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_sign, container, false)
    }
    companion object{
        val showSignAdapter = showNoSignAdapter()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        noSignRecycler.layoutManager = LinearLayoutManager(
            requireContext(),LinearLayoutManager.VERTICAL,false
        )
        noSignRecycler.adapter = showSignAdapter
        noSignRecycler.addItemDecoration(SignItemDecoration(100, 30, 100, 0))
        val refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            Repository.instance.loadAllReleasedTasks()
            Thread{
                Thread.sleep(1000)
                noSignRefresh.isRefreshing = false
            }.start()
        }
        noSignRefresh.setOnRefreshListener(refreshListener)
    }
}