package com.example.eduardomenezes.projetoeduardo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ListaNoticias: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_noticia)

        /*val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.img1024)
        val filterBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val paint = Paint()

        val canvas = Canvas(filterBitmap)
        canvas.drawRect(Rect(/*bitmap size**/), Paint())
        filter_image.setImageBitmap(filterBitmap)*/

    }
}