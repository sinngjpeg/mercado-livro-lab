package br.com.jpegsinng.mercadolivro.repository

import br.com.jpegsinng.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository : CrudRepository<PurchaseModel, Int> {}