package com.example.countdownday2

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class BgAdapter(activity: Activity, val resourceId: Int, data: List<Bg>):
    ArrayAdapter<Bg>(activity, resourceId, data){
    inner class ViewHolder(val BgImage: ImageView, val BgName: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val bgImage: ImageView = view.findViewById(R.id.bgImage)
            val bgName: TextView = view.findViewById(R.id.bgName)
            viewHolder = ViewHolder(bgImage, bgName)
            view.tag = viewHolder
        }
        else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val bg = getItem(position)
        if(bg != null){
            viewHolder.BgImage.setImageResource(bg.imageId)
            viewHolder.BgName.text = bg.name
        }
        return view
    }

}