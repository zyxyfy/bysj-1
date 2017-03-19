package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.CoTutorageDao;
import com.newview.bysj.domain.CoTutorage;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("coTutorageService")
public class CoTutorageService extends BasicService<CoTutorage, Integer> {

    CoTutorageDao coTutorageDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<CoTutorage, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        coTutorageDao = (CoTutorageDao) basicDao;
    }

}
