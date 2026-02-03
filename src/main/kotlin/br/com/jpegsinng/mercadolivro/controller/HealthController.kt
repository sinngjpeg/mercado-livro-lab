package br.com.jpegsinng.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun health(): Map<String, String>{
        return mapOf("status" to "UP")
    }
}