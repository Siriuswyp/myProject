package com.example.countdownday2

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ItemAdapter(activity: Activity, val resourceId: Int, data: List<Item>) :
    ArrayAdapter<Item>(activity, resourceId, data){

    inner class ViewHolder(val ItemImage: ImageView, val ItemName: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val itemImage: ImageView = view.findViewById(R.id.ItemImage)
            val itemName: TextView = view.findViewById(R.id.ItemName)
            viewHolder = ViewHolder(itemImage, itemName)
            view.tag = viewHolder
        }
        else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }


        val task = getItem(position)

        if(task != null){
            viewHolder.ItemImage.setImageResource(task.imageId)
            viewHolder.ItemName.text = task.name
        }
        return view
    }
}