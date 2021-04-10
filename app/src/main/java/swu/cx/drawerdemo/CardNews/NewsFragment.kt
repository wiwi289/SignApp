package swu.cx.drawerdemo.CardNews
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import swu.cx.drawerdemo.Adapter.SchoolNewsAdapter
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.Utils.SignItemDecoration

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSchoolNewsRecycler.layoutManager = LinearLayoutManager(
            activity,LinearLayoutManager.VERTICAL,false
        )
        mSchoolNewsRecycler.adapter = SchoolNewsAdapter(requireActivity())
        mSchoolNewsRecycler.addItemDecoration(SignItemDecoration(100,50,100,0))
    }


}