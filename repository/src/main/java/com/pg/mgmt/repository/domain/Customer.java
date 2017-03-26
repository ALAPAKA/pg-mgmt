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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadharId() {
        return aadharId;
    }

    public void setAadharId(String aadharId) {
        this.aadharId = aadharId;
    }

    public String getPanCardId() {
        return panCardId;
    }

    public void setPanCardId(String panCardId) {
        this.panCardId = panCardId;
    }
}
