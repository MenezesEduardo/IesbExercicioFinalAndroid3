package com.example.eduardomenezes.projetoeduardo

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AdaptAluno(private val cxt: Context) : RecyclerView.Adapter<AlunoViewHolder>() {
    private var dadosFirebase = mutableListOf<UsuarioAluno>()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val user = mAuth.getCurrentUser()

    fun setData(dados: MutableList<UsuarioAluno>) {
        dadosFirebase = dados
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val v = LayoutInflater.from(cxt).inflate(R.layout.celula_pessoa, parent, false)
        return AlunoViewHolder(v)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = dadosFirebase[position]

        holder.nomePessoa.text= aluno.nome.toString()
        holder.emailPessoa.text= aluno.email.toString()

    }


    override fun getItemCount(): Int {
        return dadosFirebase.size
    }

}
