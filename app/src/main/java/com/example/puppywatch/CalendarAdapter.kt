package com.example.puppywatch

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView



class CalendarAdapter(private val dayList: ArrayList<String>, private val onItemListener: OnItemListener):
        RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day_Text: TextView = itemView.findViewById(R.id.day_Text)
        var act_icon: ImageView = itemView.findViewById(R.id.act_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){

        var day = dayList[holder.adapterPosition]
        holder.day_Text.setText(day)

        holder.day_Text.setText(dayList[holder.adapterPosition])
        holder.act_icon.setOnClickListener{
            if (day.equals("")){
                Log.d("buttonTest", "item position: ${holder.adapterPosition}")

            }
            else {
                holder.act_icon?.context?.let { context ->
                    val dialog = CustomDialog(context)
                    dialog.detailInitViews()
                }
//                val intent = Intent(holder.act_icon?.context, MainActivity::class.java)
//                startActivity(holder.act_icon.context, intent, null)
                Log.d("clickTest", "item position: ${holder.adapterPosition}")
            }
        }


        if (day.equals("")){
            holder.act_icon.setImageResource(0)
        }
        if((position + 1 ) % 7 == 0) {
            holder.day_Text.setTextColor(android.graphics.Color.parseColor("#0000FF"))
        }else if (position==0 || position % 7 == 0){
            holder.day_Text.setTextColor(android.graphics.Color.parseColor("#FF0000"))
        }
        holder.itemView.setOnClickListener {
            onItemListener.onItemClick(day)
        }

    }
    override fun getItemCount(): Int {
        return dayList.size
    }
}

