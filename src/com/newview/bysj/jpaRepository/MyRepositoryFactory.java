package com.newview.bysj.jpaRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;


public class MyRepositoryFactory extends JpaRepositoryFactory {

    public MyRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        // TODO Auto-generated constructor stub
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {

        JpaEntityInformation<Object, Serializable> entityMetadata = mock(JpaEntityInformation.class);
        when(entityMetadata.getJavaType()).thenReturn((Class<Object>) metadata.getDomainType());
        return new MyRepositoryImplement<Object, Serializable>(entityMetadata, entityManager);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.data.repository.support.RepositoryFactorySupport#
     * getRepositoryBaseClass()
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

        return MyRepositoryImplement.class;
    }

}
