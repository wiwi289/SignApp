package swu.cx.drawerdemo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import swu.cx.drawerdemo.CardSignIn.showNameView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository

class showNoSignAdapter: RecyclerView.Adapter<showNoSignAdapter.noSignHolder>() {
    class noSignHolder(item:View):RecyclerView.ViewHolder(item){
        val showName = item.findViewById<showNameView>(R.id.showName)
        val sid = item.findViewById<TextView>(R.id.showId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noSignHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.not_sign_lists,parent,false)
        return noSignHolder(view)
    }

    override fun onBindViewHolder(holder: noSignHolder, position: Int) {
        val sInfor = Repository.instance.noSignStudentLists!![position]
        holder.showName.text = sInfor.studentname
        holder.sid.text = sInfor.student
    }

    override fun getItemCount()= Repository.instance.noSignStudentLists!!.size
}