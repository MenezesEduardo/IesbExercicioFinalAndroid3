package com.example.eduardomenezes.projetoeduardo

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class AlunoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var nomePessoa: TextView
    var emailPessoa: TextView

    init {
        nomePessoa = itemView.findViewById(R.id.nomePessoa)
        emailPessoa = itemView.findViewById(R.id.emailPessoa)

    }



}