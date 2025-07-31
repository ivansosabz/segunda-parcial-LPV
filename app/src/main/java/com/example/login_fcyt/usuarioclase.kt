    package com.example.login_fcyt

class usuarioclase {
    private var id: Int = 0
    private var usuario: String= ""
    private var password: String=""
    private var ci: String=""

    constructor(id: Int, usuario: String, password: String, ci:String) {
        this.id = id
        this.usuario = usuario
        this.password = password
        this.ci= ci


    }
    fun getId(): Int{
        return id;
    }
    fun getUsuario(): String {
        return usuario;

    }
    fun getPassword(): String {
        return password;
    }
    fun getCI(): String {
        return ci;

    }
    override fun toString(): String {
        return "usuario: "+usuario;
    }







}