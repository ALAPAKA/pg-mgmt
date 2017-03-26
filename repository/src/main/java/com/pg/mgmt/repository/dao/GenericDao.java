/**
 *
 */
package com.pg.mgmt.repository.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Siva
 * @Email: shiva.forums@gmail.com
 * @Date Dec 17, 2011 11:50:27 AM
 */
public interface GenericDao<T> {

    /**
     * Method to determine if id exist in DB
     *
     * @param id
     * @return if the Id exists
     */
    public boolean exists(Long id);

    /**
     * findById
     *
     * @param id
     * @return T
     */
    T findById(Long id);

    /**
     * findAll
     *
     * @return List<T>
     */
    List<T> findAll();

    /**
     * persist
     *
     * @param entity
     */
    T save(T entity);

    /**
     * remove
     *
     * @param entity
     */
    void remove(T entity);

    /**
     * remove the entity that have the ID
     *
     * @param id
     * @return The id of the removed id
     */
    void removeId(Long id);

}
