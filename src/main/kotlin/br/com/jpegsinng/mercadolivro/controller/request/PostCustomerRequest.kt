package br.com.jpegsinng.mercadolivro.controller.request

import br.com.jpegsinng.mercadolivro.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty


data class PostCustomerRequest (

    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @field:Email(message = "E-mail deve ser válido")
    @EmailAvailable(message = "Email em uso")
    var email: String,

    @field:NotEmpty(message = "Senha deve ser informada")
    var password: String
)
