package br.com.jpegsinng.mercadolivro.controller.response

data class ErrorResponse(
    var httpCode: Int,
    var mensagem: String,
    var internalCode: String,
    var errors: List<FieldErrorResponse>?
)