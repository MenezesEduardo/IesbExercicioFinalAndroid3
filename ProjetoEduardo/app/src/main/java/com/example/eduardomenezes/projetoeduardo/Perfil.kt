package com.example.eduardomenezes.projetoeduardo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.perfil.*
import android.arch.persistence.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Perfil: AppCompatActivity()  {

    var nome : String = ""
    var matricula : String = ""
    var telefone : String = ""
    var endereco : String = ""
    var email : String = ""
    var senha : String = ""

    private var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("aluno")

        val username = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        var mAlunoReference = FirebaseDatabase.getInstance().getReference("aluno").child(username)

// Attach a listener to read the data at our posts reference

        val alunoObjectListener = object : ValueEventListener {
            override  fun onDataChange(dataSnapshot: DataSnapshot) {
                val aluno = dataSnapshot.getValue(UsuarioAluno::class.java)
                // if (user != null){
                txtNome.setText(aluno!!.nome.toString());
                txtEmail2.setText( user!!.getEmail().toString());
                txtTelefone.setText(aluno!!.telefone.toString());
                txtEndereco.setText(aluno!!.endereco.toString());
                txtMatricula.setText(aluno!!.matricula.toString());
                // }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                System.out.println("The read failed: " + databaseError.getCode())
            }
        }
        mAlunoReference!!.addValueEventListener(alunoObjectListener)
/*

        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "room-database"
        ).allowMainThreadQueries().build()
*//*
        var imv = R.id.imgPerfil as ImageView
        imv.setOnClickListener{
            Toast.makeText(this@Perfil,"Teste Foto", Toast.LENGTH_LONG).show()
        }*/

        btnSalvar.setOnClickListener {
            if(VerificaCampos()){

                val usuarioAluno = UsuarioAluno()
                usuarioAluno.nome = txtNome.text.toString()
                usuarioAluno.telefone = txtTelefone.text.toString()
                usuarioAluno.endereco= txtEndereco.text.toString()
                usuarioAluno.matricula =txtMatricula.text.toString()
                usuarioAluno.telefone = txtTelefone.text.toString()
                mAlunoReference.setValue(usuarioAluno)

                Toast.makeText(this@Perfil, "Aluno cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                val intent = Intent(this@Perfil, Login::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@Perfil, "Validação falhou", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun VerificaCampos(): Boolean{

        var verificado = false

        if (txtNome.text.toString().trim().isNotEmpty()) {
            nome = txtNome.text.toString()
            verificado = true
        } else {
            Toast.makeText(this@Perfil, "Insira nome.", Toast.LENGTH_LONG).show()
            verificado = false
        }

        if (txtTelefone.text.toString().trim().isNotEmpty()) {
            telefone = txtTelefone.text.toString()
            verificado = true
        } else {
            Toast.makeText(this@Perfil, "Insira telefone.", Toast.LENGTH_LONG).show()
            verificado = false
        }

        if (txtMatricula.text.toString().trim().isNotEmpty()) {
            matricula = txtMatricula.text.toString()
            verificado = true
        } else {
            Toast.makeText(this@Perfil, "Insira matricula.", Toast.LENGTH_LONG).show()
            verificado = false
        }

        if (txtSenha2.text.toString().trim().isNotEmpty()) {
            if (txtConfirma2.text.toString().trim().isNotEmpty()){
                if (txtSenha2.text.toString() == txtConfirma2.text.toString()){
                    senha = txtSenha2.text.toString()
                    verificado = true
                }else{
                    Toast.makeText(this@Perfil, "Senha e confirmar senha não são iguais.", Toast.LENGTH_LONG).show()
                    verificado = false
                }
            }else{
                Toast.makeText(this@Perfil, "Insira Confirmar Senha.", Toast.LENGTH_LONG).show()
                verificado = false
            }
        } else {
            Toast.makeText(this@Perfil, "Insira Senha.", Toast.LENGTH_LONG).show()
            verificado = false
        }
        return verificado
    }
}