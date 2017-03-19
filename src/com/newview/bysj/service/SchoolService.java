package com.newview.bysj.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.SchoolDao;
import com.newview.bysj.domain.School;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("schoolService")
public class SchoolService extends BasicService<School, Integer> {

    SchoolDao schoolDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<School, Integer> basicDao) {
        this.basicDao = basicDao;
        schoolDao = (SchoolDao) basicDao;

    }


}
