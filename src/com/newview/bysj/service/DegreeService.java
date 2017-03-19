package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.DegreeDao;
import com.newview.bysj.domain.Degree;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("degreeService")
public class DegreeService extends BasicService<Degree, Integer> {

    DegreeDao degreeDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Degree, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        degreeDao = (DegreeDao) basicDao;
    }

}
