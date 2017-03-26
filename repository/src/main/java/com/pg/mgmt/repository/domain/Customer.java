package com.pg.mgmt.repository.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;

/**
 * Created by Siva on 3/26/2017.
 */
@Entity
public class Customer {

    @Id
    Long id;

    @Index
    String name;

    @Index({IfNotNull.class})
    String email;

    @Index({IfNotNull.class})
    String phone;

    @Index({IfNotNull.class})
    String aadharId;

    @Index({IfNotNull.class})
    String panCardId;


}
