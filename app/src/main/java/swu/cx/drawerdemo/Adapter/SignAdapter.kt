package swu.cx.drawerdemo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.Utils.GaoDeMap
import swu.cx.drawerdemo.data.Repository

class signAdapter(private val mContext: Context): RecyclerView.Adapter<signAdapter.signHolder>() {

    var signCallBack:((Int)->Unit)?=null
    class signHolder(item:View):RecyclerView.ViewHolder(item){
            val stateText = item.findViewById<TextView>(R.id.stateText)
            val endtime = item.findViewById<TextView>(R.id.endTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): signHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_signs_item_layout,parent,false)
            return signHolder(view)
    }

    override fun onBindViewHolder(holder: signHolder, position: Int) {
            val data = Repository.instance.signdatas!![position]
            holder.stateText.text = data.stateText
            holder.stateText.setTextColor(data.color)
            val signInfor = Repository.instance.orgAttandenceInfors!![position]
            holder.endtime.text = signInfor.kqstarttime
                val view = holder.itemView.findViewById<CardView>(R.id.signCard)
                if (data.isClickable) {
                    view.isClickable = true
                    if (GaoDeMap.isInCircle.value!!){
                        view.setOnClickListener {
                            signCallBack?.let {
                                it(position)
                            }

                        }
                    }else{
                        view.setOnClickListener {
                          Toast.makeText(mContext,"您当前不在打卡范围内",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    view.isClickable = false
                }
    }

    override fun getItemCount()= Repository.instance.signdatas!!.size
}