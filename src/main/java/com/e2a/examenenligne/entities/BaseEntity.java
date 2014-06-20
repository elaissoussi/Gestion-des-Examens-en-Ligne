package com.e2a.examenenligne.entities;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!(obj instanceof BaseEntity)) {
            return false;
        } else if (((BaseEntity) obj).id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "entity." + this.getClass() + "[ id=" + id + " ] ";
    }
}
