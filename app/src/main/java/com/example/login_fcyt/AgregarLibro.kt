package com.example.login_fcyt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarLibro : AppCompatActivity() {
    private lateinit var db: BaseSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)

        db = BaseSQLite(this)
        val titulo = findViewById<EditText>(R.id.etTitulo)
        val autor = findViewById<EditText>(R.id.etAutor)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val t = titulo.text.toString().trim()
            val a = autor.text.toString().trim()
            if (t.isEmpty() || a.isEmpty()) {
                Toast.makeText(this, "Debe ingresar título y autor", Toast.LENGTH_SHORT).show()
            } else {
                val ok = db.insertarLibro(t, a)
                if (ok) {
                    Toast.makeText(this, "Libro agregado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
