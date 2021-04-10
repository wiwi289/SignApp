package swu.cx.drawerdemo.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import swu.cx.drawerdemo.R
import swu.cx.drawerdemo.data.Repository

class SchoolNewsAdapter(val activity: Activity):RecyclerView.Adapter<SchoolNewsAdapter.schoolHolder>() {
    class schoolHolder(item:View):RecyclerView.ViewHolder(item){
        val container = item.findViewById<LinearLayout>(R.id.imgLinear)
        val content = item.findViewById<TextView>(R.id.newsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): schoolHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.school_news_layout,parent,false)
        return schoolHolder(view)
    }

    override fun onBindViewHolder(holder: schoolHolder, position: Int) {
        val data = Repository.instance.schoolNewsdatas[position]
        holder.content.setText(data.content)
        holder.container.removeAllViews()
        if (data.newsImg.isNotEmpty()){
            for (i in data.newsImg.indices){
                ImageView(activity).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        0, Repository.dpTopx(100,resources)
                    ).apply {
                        weight = 1.0f
                    }
                    setImageResource(data.newsImg[i])
                }.also {
                    holder.container.addView(it)
                }
            }
        }
    }

    override fun getItemCount()= Repository.instance.schoolNewsdatas.size
}