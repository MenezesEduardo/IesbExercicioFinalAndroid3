package com.example.eduardomenezes.projetoeduardo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtLeft: TextView
    var txtRight: TextView

    init {
        txtLeft = itemView.findViewById(R.id.txtLeft)
        txtRight = itemView.findViewById(R.id.txtRight)
    }
}