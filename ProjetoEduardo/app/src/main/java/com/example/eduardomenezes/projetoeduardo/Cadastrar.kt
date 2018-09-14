package com.example.eduardomenezes.projetoeduardo

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.cadastrar.*



class Cadastrar : AppCompatActivity() {


    private var PERMISSION_REQUEST_CODE = 200
    private var TAKE_PHOTO_REQUEST = 101
    var mCurrentPhotoPath: String = ""

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrar)
        supportActionBar?.title = "Cadastro de Usuário"

        populaNot()
        /*val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "room-database"
        ).allowMainThreadQueries().build()
*/
        btnCadastro.setOnClickListener {

            if (txtEmail.text.toString().trim().isNullOrEmpty()) {
                txtEmail?.error = "email obrigatório."
            } else {
                if (txtSenha.text.toString().trim().isNullOrEmpty()) {
                    txtSenha?.error = "senha obrigatória."
                } else {
                    if (txtConfirma.text.toString().trim().isNullOrEmpty()) {
                        txtConfirma?.error = "confirmacao obrigatória."
                    } else {

                        if (!txtSenha.text.toString().equals(txtConfirma.text.toString())) {
                            Toast.makeText(this@Cadastrar, "Senhas diferentes. Redigite.",
                                    Toast.LENGTH_LONG).show()
                            txtSenha.requestFocus();
                        } else {

                            mAuth.createUserWithEmailAndPassword(txtEmail.text.toString(),txtSenha.text.toString())


                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            val user = mAuth.getCurrentUser()
                                            Toast.makeText(this@Cadastrar, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG)
                                            writeNewUser(user!!.getUid(), txtEmail.text.toString())


                                            val intent = Intent(this@Cadastrar, Login::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                        }
                    }
                }
            }
        }
    }

    fun populaNot(){
        for(i in 1..10){
            var not = Noticia()
            var key = FirebaseDatabase.getInstance().getReference().child("noticia").push().key
            not.titulo = "noticia :" + i
            val tsLong = System.currentTimeMillis() / 1000
            not.data = tsLong.toString()
            not.descricao = "chave da noticia como descricaco : " + key

            FirebaseDatabase.getInstance().getReference().child("noticia").child(key).setValue(not)
        }
    }

    fun writeNewUser(userId: String, email: String) {
        var usuarioAluno = UsuarioAluno()
        usuarioAluno.nome = "Aluno Nome";
        usuarioAluno.email = email
        usuarioAluno.telefone = "(00)00000-0000"
        usuarioAluno.endereco = "escreva seu endereco"
        usuarioAluno.matricula = "00000000"

        FirebaseDatabase.getInstance().getReference().child("aluno").child(userId).setValue(usuarioAluno)
    }
}