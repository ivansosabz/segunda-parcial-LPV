package com.example.login_fcyt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login_fcyt.databinding.ActivityModificarBinding

private lateinit var vista: ActivityModificarBinding

class Modificar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityModificarBinding.inflate(layoutInflater)
        setContentView(vista.root)
        val DB = BaseSQLite(this)

        val usu_texto = intent.getStringExtra("usu") ?: ""
        val pass_texto = intent.getStringExtra("pass") ?: ""

        Log.d("ModificarActivity", "Usuario recibido: $usu_texto, Contraseña recibida: $pass_texto")
        vista.editTextUsuario.setText(usu_texto)
//        vista.editTextContrasenaActual.setText(pass_texto) // Mostrar la contraseña actual

        vista.buttonModificar.setOnClickListener {
            val newUsuTexto = vista.editTextUsuario.text.toString()
            val newPassTexto = vista.editTextNuevaContrasena.text.toString()
            val confirmPassTexto = vista.editTextConfirmarContrasena.text.toString()
            val currentPassTexto = vista.editTextContrasenaActual.text.toString()

            // Validación de contraseñas iguales
            if (newPassTexto != confirmPassTexto) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Validación de que la nueva contraseña no sea igual a la contraseña actual
            if (newPassTexto == pass_texto) {
                Toast.makeText(this, "La nueva contraseña no puede ser igual a la contraseña actual", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Validación de que la nueva contraseña no sea igual a la contraseña por defecto
            val defaultPasswords = listOf("123", "24462263", "1234589", "4588963") // Lista de contraseñas por defecto
            if (defaultPasswords.contains(newPassTexto)) {
                Toast.makeText(this, "La nueva contraseña no puede ser igual a una contraseña por defecto", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val modifico = DB.updatePassword(newUsuTexto, currentPassTexto, newPassTexto)
            if (modifico) {
                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_LONG).show()

                // Iniciar la actividad Bienvenido y pasar el nombre del usuario
                val volverABienvenido = Intent(applicationContext, Bienvenido::class.java).apply {
                    putExtra("nombre_enviado", newUsuTexto) // Pasar el nombre del usuario
                }
                startActivity(volverABienvenido)
            } else {
                Toast.makeText(this, "Error al actualizar la contraseña", Toast.LENGTH_LONG).show()
            }
        }
    }
}
