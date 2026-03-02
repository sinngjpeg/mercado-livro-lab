package br.com.jpegsinng.mercadolivro.exception

class AuthenticationException(override val message: String, val errorCode: String) : Exception()