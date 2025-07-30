package com.example.login_fcyt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseSQLite(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "login89"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, password TEXT, ci TEXT)")
//        db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES (?, ?, ?)", arrayOf("administrador", "24462263", "24462263"))
//        db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES (?, ?, ?)", arrayOf("juan", "1234589", "1234589"))
//        db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES (?, ?, ?)", arrayOf("admin", "4588963", "4588963"))
        db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES (?, ?, ?)", arrayOf("administrador", "123", "123"))
        db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES (?, ?, ?)", arrayOf("juan", "123", "123"))
        db?.execSQL("INSERT INTO usuarios (nombre, password, ci) VALUES (?, ?, ?)", arrayOf("admin", "123", "123"))
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
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
}
