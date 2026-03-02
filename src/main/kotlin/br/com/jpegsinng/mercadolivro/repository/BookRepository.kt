package br.com.jpegsinng.mercadolivro.repository

import br.com.jpegsinng.mercadolivro.enums.BookStatus
import br.com.jpegsinng.mercadolivro.model.BookModel
import br.com.jpegsinng.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import java.awt.print.Pageable

interface BookRepository : JpaRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}