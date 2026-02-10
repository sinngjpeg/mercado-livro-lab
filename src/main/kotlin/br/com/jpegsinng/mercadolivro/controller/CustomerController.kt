package br.com.jpegsinng.mercadolivro.controller

import br.com.jpegsinng.mercadolivro.controller.request.PostCustomerRequest
import br.com.jpegsinng.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerController {

    val customers = mutableListOf<CustomerModel>()

    @GetMapping
    fun getAll(): List<CustomerModel> {
        return customers
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest) {
        var id = if (customers.isEmpty()) {
            1
        } else {
            customers.last().id + 1
        }.toString()
        customers.add(CustomerModel("1", customer.name, customer.email))
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): CustomerModel {
       return customers.filter { it.id == id }.first()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody customer: PostCustomerRequest): CustomerModel {
        return customers.filter { it.id == id }.first()
    }

}