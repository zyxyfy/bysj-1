package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.MainTutorageDao;
import com.newview.bysj.domain.MainTutorage;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("mainTutorageService")
public class MainTutorageService extends BasicService<MainTutorage, Integer> {

    MainTutorageDao mainTutorageDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<MainTutorage, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        mainTutorageDao = (MainTutorageDao) basicDao;
    }

}
