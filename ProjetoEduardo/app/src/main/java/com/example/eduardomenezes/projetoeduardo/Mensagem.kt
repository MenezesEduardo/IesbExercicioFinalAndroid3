package com.example.eduardomenezes.projetoeduardo

class Mensagem {
    var data: String? = null
    var descricao: String? = null
    var titulo: String? = null
    var origem: String? = null

    override fun toString(): String {
        return "[Data: $data, Descrição: $descricao, Título: $titulo, Origem: $origem]"
    }
}
