package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.TutorageDao;
import com.newview.bysj.domain.Tutorage;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("tutorageService")
public class TutorageService extends BasicService<Tutorage, Integer> {

    TutorageDao tutorageDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Tutorage, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        tutorageDao = (TutorageDao) basicDao;

    }

}
