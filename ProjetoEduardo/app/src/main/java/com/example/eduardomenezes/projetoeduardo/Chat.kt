package com.example.eduardomenezes.projetoeduardo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chat.*
import java.util.*

/**
 * Created by Eduardo Menezes on 13/09/2018.
 */
class Chat : AppCompatActivity() {

    var dadosFirebase = mutableListOf<Mensagem>()
    lateinit var recyclerView: RecyclerView
    lateinit var adaptador: AdaptadorChat
    lateinit var txtMsg: TextView

    lateinit var userchat: String
    lateinit var username: String

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user = mAuth.getCurrentUser()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        txtMsg = findViewById(R.id.txtMsg)
        username = getIntent().getStringExtra("Nome")
        nomeChat.text = username

        val btn = findViewById<Button>(R.id.btnSend)
        btn.setOnClickListener {
            enviar()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        adaptador = AdaptadorChat(this)
        recyclerView.adapter = adaptador

        recuperarChat()
    }

    private fun enviar() {
        userchat = getIntent().getStringExtra("matricula")

        val uuid = UUID.randomUUID().toString()
        val db = FirebaseDatabase.getInstance()
        val dbRef = db.getReference("/mensagens").child(userchat).child(uuid)
        val n = Mensagem()

        n.origem = user!!.uid
        n.titulo = txtMsg.text.toString()
        val tsLong = System.currentTimeMillis() / 1000
        n.data = tsLong.toString()
        dbRef.setValue(n)
        txtMsg.text = ""

    }

    private fun recuperarChat() {
        val db = FirebaseDatabase.getInstance()
        userchat = getIntent().getStringExtra("matricula")
        val dbRef = db.getReference("/mensagens").child(userchat).orderByChild("data")
        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                dadosFirebase.clear()
                snapshot.children.forEach { snap ->
                    val mensagem = snap.getValue(Mensagem::class.java)
                    dadosFirebase.add(mensagem!!)
                    Log.d("FIREBASE", mensagem.toString())
                    adaptador.setData(dadosFirebase)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}