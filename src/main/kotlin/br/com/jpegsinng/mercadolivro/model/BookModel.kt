package br.com.jpegsinng.mercadolivro.model

import br.com.jpegsinng.mercadolivro.enums.BookStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var name: String,
    @Column
    var price: BigDecimal,
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null,
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
)