package swu.cx.drawerdemo.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository
import swu.cx.drawerdemo.Utils.NetWorkDao

class allLeaveInforAdapter: RecyclerView.Adapter<allLeaveInforAdapter.LeaveInforHolder>() {
    var leaveCallBack:((Int,String)->Unit)?=null
    class LeaveInforHolder(item: View):RecyclerView.ViewHolder(item){
        val startDay = item.findViewById<TextView>(R.id.startTime)
        val daycount = item.findViewById<TextView>(R.id.daycount)
        val state = item.findViewById<TextView>(R.id.state)
        val fdyID = item.findViewById<TextView>(R.id.fdyID)
        val cancelBtn = item.findViewById<Button>(R.id.cancelLeave)
        val deleteBtn = item.findViewById<Button>(R.id.deleteLeave)
        val leaved = item.findViewById<TextView>(R.id.leaved)
        init {
            Log.v("cx","视图状态：${leaved.visibility}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveInforHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_leave_infors_layout,parent,false)
        return LeaveInforHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: LeaveInforHolder, position: Int) {
        val oneLeave = Repository.instance.studentLeaveInfors!![position]
        holder.startDay.text = oneLeave.qjtimestart
        holder.daycount.text = oneLeave.qjday
        holder.state.text = oneLeave.qjstate
        holder.fdyID.text = oneLeave.fdy
        when (oneLeave.qjstate) {
            "待审批" -> {
                holder.leaved.visibility = View.INVISIBLE
                holder.deleteBtn.visibility = View.GONE
                holder.state.setTextColor(R.color.red)
                holder.cancelBtn.visibility = View.VISIBLE
                holder.cancelBtn.setOnClickListener {
                    NetWorkDao.cancelLeaveAsk(oneLeave.student,oneLeave.qjtimestart)
                    Repository.instance.UpDateAllLeaveInfors()
                }
            }
            "已销假" -> {
                holder.deleteBtn.visibility = View.GONE
                holder.cancelBtn.visibility = View.GONE
                holder.leaved.visibility = View.VISIBLE
            }
            "已批准" -> {
                holder.leaved.visibility = View.INVISIBLE
                holder.cancelBtn.visibility = View.GONE
                holder.deleteBtn.visibility = View.VISIBLE
                holder.deleteBtn.setOnClickListener {
                    leaveCallBack?.let {
                        it(position,oneLeave.qjtimestart)
                    }
                }
            }
            else->{
                holder.leaved.visibility = View.INVISIBLE
                holder.deleteBtn.visibility = View.GONE
                holder.cancelBtn.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return Repository.instance.studentLeaveInfors!!.size
    }
}