    package com.example.login_fcyt

    import android.content.ContentValues
    import android.content.Context
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper

    class BaseSQLite(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_NAME = "login89"
            private const val DATABASE_VERSION = 3  // Aumenta versión para que se ejecute onUpgrade
        }

        override fun onCreate(db: SQLiteDatabase?) {
            // Tabla de usuarios
            db?.execSQL("""
                CREATE TABLE usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT,
                    password TEXT,
                    ci TEXT
                )
            """)

            // Inserciones por defecto
            db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES ('usu', '123', '123')")
            db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES ('ario', '123', '123')")
            db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES ('admin', '123', '123')")

            // Tabla de libros
            db?.execSQL("""
                CREATE TABLE libros (
                    idlibro INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    autor TEXT NOT NULL
                )
            """)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            // Eliminar ambas tablas si existen y recrearlas
            db?.execSQL("DROP TABLE IF EXISTS usuarios")
            db?.execSQL("DROP TABLE IF EXISTS libros")
            onCreate(db)
        }

        fun existe(usuario: String?, pass: String?): Boolean {
            val db = this.readableDatabase
            val resultado = db.rawQuery("SELECT * FROM usuarios WHERE nombre=? AND password=?", arrayOf(usuario, pass))
            return resultado.count > 0
        }

        fun updatePassword(username: String, currentPassword: String, newPassword: String): Boolean {
            val db = this.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM usuarios WHERE nombre=? AND password=?", arrayOf(username, currentPassword))
            return if (cursor.count > 0) {
                val contentValues = ContentValues()
                contentValues.put("password", newPassword)
                db.update("usuarios", contentValues, "nombre = ?", arrayOf(username))
                true
            } else {
                false
            }
        }

        // 🆕 Métodos CRUD para libros

        fun insertarLibro(titulo: String, autor: String): Boolean {
            val db = this.writableDatabase
            val datos = ContentValues().apply {
                put("titulo", titulo)
                put("autor", autor)
            }
            return db.insert("libros", null, datos) > 0
        }

        fun actualizarLibro(id: Int, titulo: String, autor: String): Boolean {
            val db = this.writableDatabase
            val datos = ContentValues().apply {
                put("titulo", titulo)
                put("autor", autor)
            }
            return db.update("libros", datos, "idlibro=?", arrayOf(id.toString())) > 0
        }

        fun eliminarLibro(id: Int): Boolean {
            val db = this.writableDatabase
            return db.delete("libros", "idlibro=?", arrayOf(id.toString())) > 0
        }

        fun obtenerTodosLosLibros(): MutableList<Triple<Int, String, String>> {
            val lista = mutableListOf<Triple<Int, String, String>>()
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT idlibro, titulo, autor FROM libros", null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val titulo = cursor.getString(1)
                    val autor = cursor.getString(2)
                    lista.add(Triple(id, titulo, autor))
                } while (cursor.moveToNext())
            }
            cursor.close()
            return lista
        }

        fun obtenerLibroPorId(id: Int): Triple<Int, String, String>? {
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT idlibro, titulo, autor FROM libros WHERE idlibro=?", arrayOf(id.toString()))
            return if (cursor.moveToFirst()) {
                val idLibro = cursor.getInt(0)
                val titulo = cursor.getString(1)
                val autor = cursor.getString(2)
                cursor.close()
                Triple(idLibro, titulo, autor)
            } else {
                cursor.close()
                null
            }
        }

    }
