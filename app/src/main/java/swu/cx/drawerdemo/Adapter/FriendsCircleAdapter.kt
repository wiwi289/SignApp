package swu.cx.drawerdemo.Adapter

import android.app.Activity
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository

class FriendsCircleAdapter(val activity: Activity,val resources: Resources):RecyclerView.Adapter<FriendsCircleAdapter.friendsHolder>() {
    class friendsHolder(item:View):RecyclerView.ViewHolder(item){
        val friendCircleText = item.findViewById<TextView>(R.id.commentText)
        val frinendCircleHead = item.findViewById<ImageView>(R.id.head)
       val container = item.findViewById<LinearLayout>(R.id.circleContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): friendsHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_circle_item,parent,false)
            return friendsHolder(view)
    }

    override fun onBindViewHolder(holder: friendsHolder, position: Int) {
        val data = Repository.instance.friendsCircledatas[position]
        holder.friendCircleText.setText(data.comment)
        holder.frinendCircleHead.setImageResource(data.headIcon)
        holder.container.removeAllViews()
        if (data.contentsImg.isNotEmpty()){
            for (i in data.contentsImg.indices){
                ImageView(activity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0, Repository.dpTopx(100,resources)
                    ).apply {
                        weight=1.0f
                    }
                    setImageResource(data.contentsImg[i])
                    //scaleType=ImageView.ScaleType.FIT_CENTER
                }.also {
                    holder.container.addView(it)
                }
            }
        }
    }

    override fun getItemCount()= Repository.instance.friendsCircledatas.size


}