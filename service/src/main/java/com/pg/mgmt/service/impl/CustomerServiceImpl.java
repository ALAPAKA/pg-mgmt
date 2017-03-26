package com.pg.mgmt.service.impl;

import com.pg.mgmt.repository.dao.CustomerDao;
import com.pg.mgmt.repository.domain.Customer;
import com.pg.mgmt.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Siva on 3/26/2017.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Resource(name = "customerDao")
    private CustomerDao customerDao;

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerDao.remove(customer);
    }

    @Override
    public void delete(Long id) {
        customerDao.removeId(id);
    }
}
