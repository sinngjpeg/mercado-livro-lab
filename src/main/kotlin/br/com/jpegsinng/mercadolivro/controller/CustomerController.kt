package br.com.jpegsinng.mercadolivro.controller

import br.com.jpegsinng.mercadolivro.controller.request.PostCustomerRequest
import br.com.jpegsinng.mercadolivro.controller.request.PutCustomerRequest
import br.com.jpegsinng.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerController {

    val customers = mutableListOf<CustomerModel>()

    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerModel> {
        name?.let{
            return customers.filter { it.name.contains(name, true) }
        }
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: String, @RequestBody customer: PutCustomerRequest) {
        return customers.filter { it.id == id }.first().let {
            it.name = customer.name
            it.email = customer.email
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String){
        customers.removeIf { it.id == id }
    }

}