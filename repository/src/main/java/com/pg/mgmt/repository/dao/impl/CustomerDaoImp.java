package com.pg.mgmt.repository.dao.impl;

import com.pg.mgmt.repository.dao.CustomerDao;
import com.pg.mgmt.repository.dao.GenericDao;
import com.pg.mgmt.repository.domain.Customer;
import org.springframework.stereotype.Repository;

/**
 * Created by Siva on 3/26/2017.
 */
@Repository("customerDao")
public class CustomerDaoImp extends GenericDaoObjectifyImpl<Customer> implements CustomerDao {
}
