/**
 *
 */
package com.pg.mgmt.repository.dao.impl;

import com.pg.mgmt.repository.dao.GenericDao;
import com.pg.mgmt.repository.dao.entity.DomainObject;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Generic Implementation of DAO with basic CRUD operations, And is expected to
 * be Extended by Specific DAO's to persist Specific Entity types. <br>
 *
 * @author Siva
 * @Email: shiva.forums@gmail.com
 * @Date Jan 24, 2012 7:50:42 PM
 */
public abstract class GenericDaoObjectifyImpl<T> implements GenericDao<T> {

    protected org.slf4j.Logger Logger;


    protected Class<T> persistentClass;

    /**
     * This is default constructor used when this generic dao is extended by
     * specific dao's.
     */
    @SuppressWarnings("unchecked")
    public GenericDaoObjectifyImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Logger = LoggerFactory.getLogger(this.persistentClass);
    }

    /**
     * This constructor is expected to be used when instantiating this generic dao
     * instead of specific one. This is generally used in spring bean definitions,
     * (use as an abstract bean definition and extended in each specific case by
     * passing constructor-arg's, this will simplify the code by reusing generic
     * dao implementation instead of creating specific dao's, we can use generic
     * one in specific cases as well.
     * <p/>
     * At the same time this will simplify the spring bean definitions by creating
     * abstract bean definition with generic dao at once and extending that bean
     * definition and creating specific bean definitions by passing
     * constructor-arg as specific entity class name).
     *
     * @param persistentClass
     */
    public GenericDaoObjectifyImpl(final Class<T> persistentClass) {
        super();
        this.persistentClass = persistentClass;
        Logger = LoggerFactory.getLogger(this.persistentClass);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.boshanam.user.core.persistence.dao.IGenericDao#exists(java.io.Serializable
     * )
     */
    @Override
    public boolean exists(Long id) {
        return (id != null) ? (ofy().load().type(persistentClass).id(id).now() != null) : false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.boshanam.user.core.persistence.dao.IGenericDao#findById(java.io.
     * Serializable)
     */
    @Override
    public T findById(Long id) {
        Logger.debug("findById({}) - Querying all entities of kind '{}' ", id, persistentClass);
        return ofy().load().type(persistentClass).id(id).now();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.boshanam.user.core.persistence.dao.IGenericDao#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        Logger.debug("findAll() - Querying all entities of kind '{}' ", persistentClass);
        return ofy().load().type(persistentClass).list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.boshanam.user.core.persistence.dao.IGenericDao#persist(java.lang.Object
     * )
     */
    @Override
    public T save(T entity) {
        Logger.debug("DAO persist()  {}", entity);
        ofy().save().entity(entity).now();
        return entity;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.boshanam.user.core.persistence.dao.IGenericDao#remove(java.lang.Object)
     */
    @Override
    public void remove(T entity) {
        Logger.debug("DAO remove()  {}", entity);
        if (entity != null) {
            ofy().delete().entity(entity).now();
        }
    }

    /**
     */
    @Override
    public void removeId(Long id) {
        Logger.debug("DAO removeId()  {}", id);
        ofy().delete().type(this.persistentClass).id(id).now();
    }

    /**
     * @return the persistentClass
     */
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * @param persistentClass the persistentClass to set
     */
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

}
