package com.pg.mgmt.api;

import com.pg.mgmt.repository.domain.Customer;
import com.pg.mgmt.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Siva on 3/26/2017.
 */
@RestController
@RequestMapping("/customer")
public class CustomerResource {

    @Resource(name = "customerService")
    private CustomerService customerService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer getById(@RequestParam Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id should not be null");
        }
        return customerService.findById(id);
    }

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer save(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer entity should not be null.");
        }
        return customerService.save(customer);
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer delete(@RequestParam Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id should not be null.");
        }
        Customer customer = customerService.findById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with given ID.");
        }
        customerService.delete(id);
        return customer;
    }
}
