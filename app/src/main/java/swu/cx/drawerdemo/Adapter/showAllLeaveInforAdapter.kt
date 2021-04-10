package swu.cx.drawerdemo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import swu.cx.drawerdemo.CardSignIn.showNameView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository

class showAllLeaveInforAdapter: RecyclerView.Adapter<showAllLeaveInforAdapter.allLeaveHolder>() {
    class allLeaveHolder(item: View):RecyclerView.ViewHolder(item){
        val showName = item.findViewById<showNameView>(R.id.leaveShowName)
        val leaveid = item.findViewById<TextView>(R.id.leaveId)
        val leaveday = item.findViewById<TextView>(R.id.leaveDay)
        val leavestart = item.findViewById<TextView>(R.id.leaveStart)
        val leavereason = item.findViewById<TextView>(R.id.leaveReason)
        val leavestate = item.findViewById<TextView>(R.id.leaveState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): allLeaveHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_show_all_leaves,parent,false)
        return allLeaveHolder(view)
    }

    override fun onBindViewHolder(holder: allLeaveHolder, position: Int) {
        if (position== Repository.instance.allStudentLeaves!!.size){
            holder.itemView.visibility = View.GONE
        }else {
            val leaveInfor = Repository.instance.allStudentLeaves!![position]
            holder.showName.text = leaveInfor.studentname
            holder.leaveid.text = leaveInfor.student
            holder.leaveday.text = leaveInfor.qjday
            holder.leavestart.text = leaveInfor.qjtimestart
            holder.leavereason.text = leaveInfor.qjcause
            holder.leavestate.text = leaveInfor.qjstate
            holder.itemView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount()=(Repository.instance.allStudentLeaves!!.size+1)

}