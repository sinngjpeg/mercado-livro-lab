package br.com.jpegsinng.mercadolivro.repository

import br.com.jpegsinng.mercadolivro.model.BookModel
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<BookModel, Int> {
}