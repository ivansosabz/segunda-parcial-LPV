package com.example.login_fcyt

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login_fcyt.databinding.ActivityBienvenidoBinding

class Bienvenido : AppCompatActivity() {
    private lateinit var vista: ActivityBienvenidoBinding
    private lateinit var db: BaseSQLite
    private lateinit var adapter: ArrayAdapter<String>
    private var listaIdLibros = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityBienvenidoBinding.inflate(layoutInflater)
        setContentView(vista.root)

        db = BaseSQLite(this)

        val nombreRecibido = intent.getStringExtra("nombre_enviado") ?: "administrador"
        vista.nombre.text = "Bienvenido, $nombreRecibido"

        vista.btnAgregarLibro.setOnClickListener {
            Toast.makeText(this, "Abrir Agregar Libro", Toast.LENGTH_SHORT).show()  // Para debug
            val intent = Intent(this, AgregarLibro::class.java)
            startActivity(intent)
        }

        cargarLibros()

        vista.listaLibros.setOnItemClickListener { _, _, position, _ ->
            val idLibro = listaIdLibros[position]
            val intent = Intent(this, EditarLibro::class.java)
            intent.putExtra("idlibro", idLibro)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cargarLibros() // Refresca la lista al volver
    }

    private fun cargarLibros() {
        val libros = db.obtenerTodosLosLibros()
        val titulos = libros.map { (id, titulo, autor) ->
            "$titulo - $autor"
        }
        listaIdLibros = libros.map { it.first }.toMutableList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos)
        vista.listaLibros.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucito, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.salir -> {
                startActivity(Intent(this, Login::class.java))
                true
            }
            R.id.apagar -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
