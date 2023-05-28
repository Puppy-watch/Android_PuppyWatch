package com.example.puppywatch

import android.content.Context
import android.content.SharedPreferences
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



class CalendarAdapter(private val sharedPreferences: SharedPreferences, private val dayList: ArrayList<String>,
                      private val onItemListener: OnItemListener, private val yearMonth: String):
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>(), MostBehaviorView {

    var holderList = ArrayList<ItemViewHolder>()
    var click_date : String = ""

    companion object {
        var dog_idx: Int = 0
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day_Text: TextView = itemView.findViewById(R.id.day_Text)
        var act_icon: ImageView = itemView.findViewById(R.id.act_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){

        // dog_idx 가져오기
        dog_idx = sharedPreferences.getInt("dog_idx", 0)
        Log.d("CalendarActivity / dog_idx", dog_idx.toString())

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
                //클릭한 날짜 담아서 리스폰스 받아오기
                click_date = yearMonth + holder.day_Text.text.toString()
                val authService = AuthService()
                holder.act_icon?.context?.let { context ->
                    val dialog = CustomDialog(context,authService,click_date)
                    dialog.detailInitViews()
                }
//                val intent = Intent(holder.act_icon?.context, MainActivity::class.java)
//                startActivity(holder.act_icon.context, intent, null)
                Log.d("clickTest", "item position: ${holder.adapterPosition}")
            }
        }

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
        //authService.setMostBehaviorView(this)
        Log.d("CalendarActivity / dog_idx", dog_idx.toString())
        authService.mostBehavior(dog_idx)
    }

    override fun onMostBehaviorSuccess(data: List<ListData>) {
        Log.d("Calendar", "성공")
        makeCalendarIcon(holderList,data)
    }

    override fun onMostBehaviorFailure() {
        Log.d("Calendar", "실패")
    }

    fun makeCalendarIcon(holders: ArrayList<ItemViewHolder>, data: List<ListData>){

        //날짜와 일치하는 통계 데이터 있으면 아이콘 변경
        for(holder in holders){
            for(item in data){
                if ((holder.day_Text.text != "")){

                    if ((holder.day_Text.text.toString().toInt()) < 10) {
                        if(item.Date == yearMonth + "0" + holder.day_Text.text){
                            changeOrangeIcon(item.mostBehav,holder.act_icon)
                        }
                    }
                    else {
                        if (item.Date == yearMonth +holder.day_Text.text){
                            changeOrangeIcon(item.mostBehav,holder.act_icon)
                        }
                    }
                }
            }
        }
    }

    fun changeOrangeIcon(act: String, iconId: ImageView) {
        when (act) {
            "eat" -> iconId.setImageResource(R.drawable.ic_cal_eat)
            "run" -> iconId.setImageResource(R.drawable.ic_cal_run)
            "bite" -> iconId.setImageResource(R.drawable.ic_cal_bite)
            "sleep" -> iconId.setImageResource(R.drawable.ic_cal_lie)
            "stand" -> iconId.setImageResource(R.drawable.ic_cal_stand)
            "seat" -> iconId.setImageResource(R.drawable.ic_cal_sit)
            else -> iconId.setImageResource(R.drawable.ic_cal_walk)
        }
    }
}
