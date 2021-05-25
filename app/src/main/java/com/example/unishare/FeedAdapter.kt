package com.example.unishare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter


class FeedAdapter(val context : Context, val posts : List<PostItem>?) : Adapter<FeedAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var title : TextView
        var desc : TextView
        init {
            title = view.findViewById(R.id.post_title)
            desc = view.findViewById(R.id.post_desc)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = posts?.get(position)?.header.toString()
        holder.desc.text = posts?.get(position)?.description.toString()
    }

    override fun getItemCount(): Int {
        return posts?.size!!
    }
}