package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.OpinionDao;
import com.newview.bysj.domain.Opinion;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("opinionService")
public class OpinionService extends BasicService<Opinion, Integer> {

    OpinionDao opinionDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Opinion, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        opinionDao = (OpinionDao) basicDao;
    }

}
