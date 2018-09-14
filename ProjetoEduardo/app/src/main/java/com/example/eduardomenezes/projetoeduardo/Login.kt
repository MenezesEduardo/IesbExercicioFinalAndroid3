package com.example.eduardomenezes.projetoeduardo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*


class Login : AppCompatActivity() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // botões
        // -----------------------
        btnEntrar.setOnClickListener {
            if(TxtEmail.text.toString().trim().isNullOrEmpty()){
                TxtEmail?.error = "Email obrigatório."
                TxtEmail.requestFocus()

            }else{
                if(TxtSenha.text.toString().trim().isNullOrEmpty()){
                    TxtSenha?.error = "Senha obrigatória."
                    TxtSenha.requestFocus()
                }else{
                    mAuth.signInWithEmailAndPassword(TxtEmail.text.toString(), TxtSenha.text.toString())
                            .addOnCompleteListener(this) { task ->

                                // tentativa de login
                                if (task.isSuccessful) {
                                    Log.w("TAG", "signInWithEmail - OK ")
                                    Toast.makeText(this@Login, "Autenticado!.",
                                            Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@Login, ListagemNoticia::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else{
                                    Log.w("TAG", "signInWithEmail - fail ", task.exception)
                                    Toast.makeText(this@Login, "Erro na autenticação: " + task.exception, Toast.LENGTH_LONG).show()
                                }
                            }
                }
            }
            //navega a proxima activity
            /*
            val intent = Intent(this@Login, ListaNoticias::class.java)
            startActivity(intent)
            finish()*/
        }

        linkCadastrar.setOnClickListener {
            //navega a proxima activity
            val intent = Intent(this@Login, Cadastrar::class.java)
            startActivity(intent)
        }

        linkRecuperar.setOnClickListener {
            //navega a proxima activity
            val intent = Intent(this@Login, RecuperarSenha::class.java)
            startActivity(intent)
        }

    }
/*
    fun CheckCredentials(): Boolean {
        var checado = false

        if (){
            checado = true
        }else{
            checado = false
        }
        //faz verificações necessárias para completar o login
        return checado
    }

    fun CheckEmail():Boolean{
        //verifica se o email é válido
        return true
    }
*/
}