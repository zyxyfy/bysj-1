package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ValidityDao;
import com.newview.bysj.domain.Validity;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("validityService")
public class ValidityService extends BasicService<Validity, Integer> {

    ValidityDao validityDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Validity, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        validityDao = (ValidityDao) basicDao;
    }

}
