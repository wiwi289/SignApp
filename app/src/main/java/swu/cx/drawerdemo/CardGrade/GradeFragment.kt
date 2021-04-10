package swu.cx.drawerdemo.CardGrade

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import swu.cx.drawerdemo.R
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.fragment_grade.*
import swu.cx.drawerdemo.MainActivity

class GradeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grade, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MainActivity.uri.let {
            gradeIcon.setImageURI(it)
        }
    }

}