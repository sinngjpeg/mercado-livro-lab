package br.com.jpegsinng.mercadolivro.controller.response

import br.com.jpegsinng.mercadolivro.enums.CustomerStatus

data class CustomerResponse(
    var id: Int? = null,
    var name: String,
    var email: String,
    var status: CustomerStatus
)