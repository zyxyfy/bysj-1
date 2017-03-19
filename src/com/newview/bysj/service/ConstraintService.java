package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ConstraintDao;
import com.newview.bysj.domain.Constraint;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("constraintService")
public class ConstraintService extends BasicService<Constraint, Integer> {

    ConstraintDao constraintDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Constraint, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        constraintDao = (ConstraintDao) basicDao;
    }

}
