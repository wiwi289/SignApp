package swu.cx.drawerdemo.CardNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.fragment_friends.*
import swu.cx.drawerdemo.Adapter.FriendsCircleAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.Utils.SignItemDecoration


class FriendsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFriendCricleRecycler.layoutManager = LinearLayoutManager(
            activity,LinearLayoutManager.VERTICAL,false
        )
        mFriendCricleRecycler.adapter = FriendsCircleAdapter(requireActivity(),resources)
        LinearSnapHelper().attachToRecyclerView(mFriendCricleRecycler)
        mFriendCricleRecycler.addItemDecoration(SignItemDecoration(10,10,30,0))
    }


}