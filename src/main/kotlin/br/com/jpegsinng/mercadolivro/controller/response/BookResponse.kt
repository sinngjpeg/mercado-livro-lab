package br.com.jpegsinng.mercadolivro.controller.response

import br.com.jpegsinng.mercadolivro.enums.BookStatus
import br.com.jpegsinng.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: CustomerModel? = null,
    var status: BookStatus? = null
)
