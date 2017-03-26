package com.pg.mgmt.repository.dao.entity;


import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class DomainObjectImpl implements DomainObject {

    public abstract Long getId();

    public abstract void setId(Long id);

    /**
     * Tests whether ID are equal between current object and other object
     *
     * @param object
     * @return
     * @throws NullPointerException     if inputs are null
     * @throws IllegalArgumentException if input types are different
     */
    public boolean hasSameIdSameClass(Object object) {
        if (object == null)
            throw new NullPointerException("cannot compare Ids of null inputs");

        if (this.getClass().equals(object.getClass()) == false)
            throw new IllegalArgumentException("Cannot check Id equality of different types [" + this.getClass().getName() + "] and ["
                    + object.getClass().getName() + "]");

        @SuppressWarnings("unchecked")
        DomainObjectImpl otherObject = (DomainObjectImpl) object;

        if (this.getId() == null || otherObject.getId() == null) {
            throw new NullPointerException("cannot compare null Ids");
        }

        return this.getId().equals(otherObject.getId());

    }

    public <T extends DomainObjectImpl> boolean removeFromCollection(Collection<T> collection) {
        if (collection == null) {
            throw new NullPointerException("inputs cannot be null");
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            T element = it.next();
            if (this.hasSameIdSameClass(element) == true) {
                return collection.remove(element);
            }
        }
        return false;
    }

    public <T extends DomainObjectImpl> boolean isInCollection(Collection<T> collection) {
        if (collection == null) {
            throw new NullPointerException("inputs cannot be null");
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            T element = it.next();
            if (this.hasSameIdSameClass(element)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        Class<? extends DomainObjectImpl> objClass = getClass();
        int result = objClass.hashCode();
        // here if id is null, then we are falling back to native hash code of
        // Object as we don't want two DTO/Entities without id assigned to be
        // treated as
        // equal. If we return different hash code for different entities
        result = prime * result + ((getId() == null) ? super.hashCode() : getId().hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DomainObject other = (DomainObject) obj;
        if (getId() == null) {
            // here we have to treat objects as unequal if any of the ID is NULL or
            // even both Entitie's ID's are NULL, this allows us to store more than
            // one Transient (Entity objects not yet persisted) into Set or Hash based
            // collections.
            return false;
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

}
