package com.example.eduardomenezes.projetoeduardo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

class AdaptadorChat(private val cxt: Context) : RecyclerView.Adapter<ChatViewHolder>() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user = mAuth.getCurrentUser()
    private var dadosFirebase = mutableListOf<Mensagem>()


    fun setData(dados: MutableList<Mensagem>) {
        dadosFirebase = dados
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val v = LayoutInflater.from(cxt).inflate(R.layout.celula_chat, parent, false)
        return ChatViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val noticia = dadosFirebase[position]

        if (noticia.origem == user!!.uid) {
            holder.txtRight.text = noticia.titulo
            holder.txtRight.visibility = View.VISIBLE
            holder.txtLeft.visibility = View.GONE
        } else {
            holder.txtLeft.text = noticia.titulo
            holder.txtLeft.visibility = View.VISIBLE
            holder.txtRight.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dadosFirebase.size
    }
}