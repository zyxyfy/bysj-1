package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ProTitleDao;
import com.newview.bysj.domain.ProTitle;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("proTitleService")
public class ProTitleService extends BasicService<ProTitle, Integer> {

    ProTitleDao proTitleDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ProTitle, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        proTitleDao = (ProTitleDao) basicDao;
    }

}
