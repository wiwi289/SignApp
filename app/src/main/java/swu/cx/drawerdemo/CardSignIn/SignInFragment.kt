package swu.cx.drawerdemo.CardSignIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_sign_in.*
import swu.cx.drawerdemo.Adapter.signAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Utils.SignItemDecoration
import swu.cx.drawerdemo.Utils.BuildDialog
import swu.cx.drawerdemo.Utils.GaoDeMap

class SignInFragment : Fragment() {
    private lateinit var signadapter:signAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signadapter=signAdapter(requireContext())
        mSignInRecycler.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )

        signadapter.signCallBack={ position->
            BuildDialog.BuildSignDialog(requireContext(), requireActivity(), signadapter, position)
        }
        mSignInRecycler.adapter = signadapter
        LinearSnapHelper().attachToRecyclerView(mSignInRecycler)
        mSignInRecycler.scrollToPosition(signadapter.itemCount-1)
        mSignInRecycler.addItemDecoration(SignItemDecoration(100, 50, 100, 0))
        //设置下拉刷新
        val refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
           Repository.instance.loadAllSignTasks()
            Thread{
                Thread.sleep(1000)
                swipeRefresh.isRefreshing = false
                requireActivity().runOnUiThread {
                    signadapter.notifyDataSetChanged()
                }
            }.start()
        }
        swipeRefresh.setOnRefreshListener(refreshListener)
        floatMapBtn.setOnClickListener {
            GaoDeMap.showMap()
        }
        GaoDeMap.isInCircle.observe(requireActivity()){ isIncircle ->
            if (!isIncircle){
                Repository.instance.signdatas?.forEach {
                    if (it.isClickable){
                        it.isClickable = false
                    }
                }
                signadapter.notifyDataSetChanged()
            }
        }
    }


}