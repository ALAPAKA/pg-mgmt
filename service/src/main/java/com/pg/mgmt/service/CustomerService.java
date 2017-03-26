package com.pg.mgmt.service;

import com.pg.mgmt.repository.domain.Customer;

import java.util.List;

/**
 * Created by Siva on 3/26/2017.
 */
public interface CustomerService {

    Customer findById(Long id);

    List<Customer> findAll();

    Customer save(Customer customer);

    void delete(Customer customer);

    void delete(Long id);
}
