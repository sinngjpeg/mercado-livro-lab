package br.com.jpegsinng.mercadolivro.exception

class NotFoundException(override val message: String, val errorCode: String) : Exception() {
}