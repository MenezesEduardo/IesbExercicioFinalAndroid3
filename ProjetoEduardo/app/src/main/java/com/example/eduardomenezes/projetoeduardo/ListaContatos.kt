package com.example.eduardomenezes.projetoeduardo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaContatos : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adaptador: AdaptAluno
    var dadosFirebase = mutableListOf<UsuarioAluno>()
    val db = FirebaseDatabase.getInstance()
    val dbRef = db.getReference("/aluno")
    var alunos = UsuarioAluno()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_pessoas)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        adaptador = AdaptAluno(this)
        recyclerView.adapter = adaptador

        recuperarNoticias()

        recyclerView.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val intent = Intent(this@ListaContatos, Chat::class.java)
                intent.putExtra("matricula", dadosFirebase[position].matricula.toString())
                intent.putExtra("Nome", dadosFirebase[position].nome.toString())

                Toast.makeText(this@ListaContatos, dadosFirebase[position].matricula.toString(),
                        Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        })
    }


    private fun recuperarNoticias() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dadosFirebase.clear()
                snapshot.children.forEach { snap ->
                    alunos = snap.getValue(UsuarioAluno::class.java)!!
                    dadosFirebase.add(alunos!!)
                    Log.d("FIREBASE", alunos.toString())
                    adaptador.setData(dadosFirebase)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View?) {
                view?.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View?) {
                view?.setOnClickListener({
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                })
            }
        })
    }
}

