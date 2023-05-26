package com.example.puppywatch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.puppywatch.response.ListData
import com.example.puppywatch.view.MostBehaviorView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class onCalendarAdapter(private val dayList: ArrayList<String>, private val onItemListener: OnItemListener):
        RecyclerView.Adapter<onCalendarAdapter.ItemViewHolder>(), MostBehaviorView {

    var holderList = ArrayList<ItemViewHolder>()

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day_Text: TextView = itemView.findViewById(R.id.day_Text)
        var act_icon: ImageView = itemView.findViewById(R.id.act_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){

        mostBehavior()

        var day = dayList[holder.adapterPosition]
        holder.day_Text.setText(day)

        holderList.add(holder)

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

        Log.d("Calendar", "여기까진 돼")
        Log.d("Calendar", holderList.toString())

        holder.act_icon.setImageResource(0)

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
    private fun mostBehavior() {
        Log.d("MostBehavior()", "메소드")

        val authService = AuthService()
        authService.setMostBehaviorView(this)
        authService.mostBehavior(1)
    }

    override fun onMostBehaviorSuccess(data: List<ListData>) {
        Log.d("Calendar", "성공")
        makeCalendarIcon(holderList,data)

    }

    override fun onMostBehaviorFailure() {
        Log.d("Calendar", "실패")
    }

    fun makeCalendarIcon(holders: ArrayList<ItemViewHolder>, data: List<ListData>){
        Log.d("Calendar", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        val cal = Calendar.getInstance()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-")
        val currentDate = cal.time
        val date = df.format(currentDate)



        for(holder in holders){
            for(item in data){
                if ((holder.day_Text.text != "")){

                    if ((holder.day_Text.text.toString().toInt()) < 10) {
                        if(item.Date == date + "0" + holder.day_Text.text){
                            changeOrangeIcon(item.mostBehav,holder.act_icon)
                        }
                    }
                    else {
                        if (item.Date == date +holder.day_Text.text){
                            changeOrangeIcon(item.mostBehav,holder.act_icon)
                        }

                }
                }
            }
        }

    }
    fun changeOrangeIcon(act: String, iconId: ImageView) {
        when (act) {
            "walk" -> iconId.setImageResource(R.drawable.ic_cal_walk)
            "run" -> iconId.setImageResource(R.drawable.ic_cal_run)
            "bite" -> iconId.setImageResource(R.drawable.ic_cal_bite)
            "lie" -> iconId.setImageResource(R.drawable.ic_cal_lie)
            "stand" -> iconId.setImageResource(R.drawable.ic_cal_stand)
            "walk" -> iconId.setImageResource(R.drawable.ic_cal_walk)
            "sit" -> iconId.setImageResource(R.drawable.ic_cal_sit)
            else -> iconId.setImageResource(R.drawable.ic_cal_eat)
        }

    }


}

