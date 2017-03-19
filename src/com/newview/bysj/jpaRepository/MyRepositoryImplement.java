package com.newview.bysj.jpaRepository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //必须的
public class MyRepositoryImplement<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {


    @SuppressWarnings("unused")
    private final EntityManager entityManager;


    public MyRepositoryImplement(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        // TODO Auto-generated constructor stub

        entityManager = em;
    }

    public MyRepositoryImplement(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void merge(T entity) {
        // TODO Auto-generated method stub
        entityManager.merge(entity);
    }

    @Override
    public int count(Class<T> entityClass) {
        // TODO Auto-generated method stub
        if (entityClass != null) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> critQuery = criteriaBuilder.createQuery(Long.class);
            Root<T> root = critQuery.from(entityClass);

            critQuery.select(criteriaBuilder.countDistinct(root));
            int count = entityManager.createQuery(critQuery).getSingleResult().intValue();
            return count;
        }
        return 0;
    }

    public List<Object[]> find(Class entityClass, String property) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.array(root.get(property)));
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }


}