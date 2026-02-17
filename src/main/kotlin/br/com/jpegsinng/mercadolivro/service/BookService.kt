package br.com.jpegsinng.mercadolivro.service

import br.com.jpegsinng.mercadolivro.model.BookModel
import br.com.jpegsinng.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

}
