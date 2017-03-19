package com.newview.bysj.jpaRepository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    public void merge(T entity);

    public int count(Class<T> entityClass);

    public EntityManager getEntityManager();

    public List<Object[]> find(Class entityClass, String property);

}
