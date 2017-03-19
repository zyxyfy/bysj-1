package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.BackUpDao;
import com.newview.bysj.domain.BackUp;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.service.BasicService;


@Service("backUpService")
public class BackUpService extends BasicService<BackUp, Integer> {

    BackUpDao backUpDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<BackUp, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        backUpDao = (BackUpDao) basicDao;
    }

}
