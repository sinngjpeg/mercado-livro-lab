package br.com.jpegsinng.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.NotEmpty

data class PostBookRequest(

    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @field:NotNull(message = "Price deve ser informado")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
)