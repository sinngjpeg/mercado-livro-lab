package br.com.jpegsinng.mercadolivro.repository

import org.springframework.data.repository.CrudRepository
import br.com.jpegsinng.mercadolivro.model.CustomerModel

interface CustomerRepository : CrudRepository<CustomerModel, Int> {
    fun findByNameContaining(name: String): List<CustomerModel>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): CustomerModel?
}