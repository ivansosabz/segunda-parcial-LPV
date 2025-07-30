package com.example.login_fcyt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login_fcyt.databinding.ActivityLoginBinding

private lateinit var vista: ActivityLoginBinding

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vista = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(vista.root)

        vista.ingresar.setOnClickListener {
            val usu = vista.usuario.text.toString()
            val pass = vista.password.text.toString()

            val DB = BaseSQLite(this)

            // Verificar si el usuario y la contraseña son correctos
            if (DB.existe(usu, pass)) {
                Toast.makeText(this, "Usuario correcto", Toast.LENGTH_LONG).show()
                handleLogin(usu, pass, DB)
            } else {
                Toast.makeText(this, "Usuario incorrecto o contraseña incorrectos", Toast.LENGTH_LONG).show()
                vista.cancelar.performClick()
            }
        }

        vista.cancelar.setOnClickListener {
            vista.usuario.setText("")
            vista.password.setText("")
        }
    }

    private fun handleLogin(usuario: String, password: String, db: BaseSQLite) {
        db.readableDatabase.rawQuery("SELECT ci FROM usuarios WHERE nombre=?", arrayOf(usuario)).use { cursor ->
            if (cursor.moveToFirst()) {
                val ci = cursor.getString(0)
                if (password == ci) {
                    // Si la contraseña coincide con el CI, abrir la actividad de modificar
                    val ventanaModificar = Intent(applicationContext, Modificar::class.java).apply {
                        putExtra("usu", usuario)
                        putExtra("pass", password)
                    }
                    startActivity(ventanaModificar)
                } else {
                    // Si la contraseña no coincide con el CI, iniciar sesión directamente
                    val ventanaBienvenido = Intent(applicationContext, Bienvenido::class.java).apply {
                        putExtra("nombre_enviado", usuario)
                    }
                    startActivity(ventanaBienvenido)
                }
            }
        }
    }
}
