package com.example.eduardomenezes.projetoeduardo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListagemNoticia : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    lateinit var adaptador: AdaptadorAluno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_noticias)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        adaptador = AdaptadorAluno(this)
        recyclerView.adapter = adaptador

        recuperarNoticias()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id: Int = item!!.itemId
        if (id == R.id.perfilnav){
            val intent = Intent(this@ListagemNoticia, Perfil::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.lista_chat){
            val intent = Intent(this@ListagemNoticia, Chat::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.Lista_contatos){
            val intent = Intent(this@ListagemNoticia, ListaContatos::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun recuperarNoticias() {
        var dados = mutableListOf<Noticia>()
        val db = FirebaseDatabase.getInstance()
        val dbRef = db.getReference("/noticia").orderByChild("data")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                dados.clear()
                snapshot.children.forEach { snap ->
                    val noticia = snap.getValue(Noticia::class.java)
                    dados.add(noticia!!)
                    Log.d("FIREBASE", noticia.toString())
                    adaptador.setData(dados)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}

