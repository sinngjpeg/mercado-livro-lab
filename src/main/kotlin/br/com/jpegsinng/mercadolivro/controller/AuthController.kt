package br.com.jpegsinng.mercadolivro.controller


import br.com.jpegsinng.mercadolivro.controller.request.LoginRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class AuthController {

    @PostMapping
    fun login(@RequestBody request: LoginRequest) {
        // O Spring Security (AuthenticationFilter) vai interceptar
        // essa chamada antes mesmo de chegar aqui.
        // Deixamos vazio apenas para o Swagger mapear os campos.
    }
}