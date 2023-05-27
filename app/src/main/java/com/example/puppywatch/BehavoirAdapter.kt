package com.example.puppywatch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.puppywatch.response.AbListData

class BehavoirAdapter(val abnormalList: List<AbListData>): RecyclerView.Adapter<BehavoirAdapter.ViewHolder>() {
    val TAG: String = "BehavoirAdapter"

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BehavoirAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_behavior_item, parent, false)
        return ViewHolder(view)
    }
    // 리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return abnormalList.size
    }
    // View에 내용 입력
    override fun onBindViewHolder(holder: BehavoirAdapter.ViewHolder, position: Int) {
        Log.d(TAG, " - onBindViewHolder() called / position: $position")

        if(abnormalList.size != 0) {
            Log.d("date", abnormalList[position].abnormalTime)
            Log.d("behavior", abnormalList[position].abnormalName)
            holder.detail.text = abnormalList[position].abnormalTime + " " + abnormalList[position].abnormalName
        }

        // CardView의 배경색 설정
        val cardView = holder.itemView.findViewById<CardView>(R.id.board_list_item_cv)
        cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.light_gray))
    }
    // 레이아웃 내 View 연결
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val detail: TextView = itemView.findViewById(R.id.board_behavior_item_tv)
    }
}