package com.example.login_fcyt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarLibro : AppCompatActivity() {
    private lateinit var db: BaseSQLite
    private var idLibro = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_libro)

        val etTitulo = findViewById<EditText>(R.id.etTituloEditar)
        val etAutor = findViewById<EditText>(R.id.etAutorEditar)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambios)

        db = BaseSQLite(this)
        idLibro = intent.getIntExtra("idlibro", -1)

        if (idLibro == -1) {
            Toast.makeText(this, "Libro no válido", Toast.LENGTH_SHORT).show()
            finish()
        }

        val libro = db.obtenerLibroPorId(idLibro)
        libro?.let { (_, titulo, autor) ->
            etTitulo.setText(titulo)
            etAutor.setText(autor)
        }

        btnGuardar.setOnClickListener {
            val nuevoTitulo = etTitulo.text.toString()
            val nuevoAutor = etAutor.text.toString()
            db.actualizarLibro(idLibro, nuevoTitulo, nuevoAutor)
            Toast.makeText(this, "Libro actualizado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
