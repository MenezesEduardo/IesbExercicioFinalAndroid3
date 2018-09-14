package com.example.eduardomenezes.projetoeduardo

class Noticia {
    var data: String? = null
    var descricao: String? = null
    var titulo: String? = null

    override fun toString(): String {
        return "[Data: $data, Descrição: $descricao, Título: $titulo]"
    }
}