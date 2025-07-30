package com.example.login_fcyt

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.login_fcyt.databinding.ActivityBienvenidoBinding

private lateinit var vista: ActivityBienvenidoBinding

class Bienvenido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityBienvenidoBinding.inflate(layoutInflater)
        setContentView(vista.root)

        // Recibir el nombre del usuario desde el Intent
        val nombreRecibido = intent.getStringExtra("nombre_enviado") ?: "administrador"

        // Establecer el texto de bienvenida
        vista.nombre.text = "Bienvenido, $nombreRecibido"
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
