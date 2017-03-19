package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.GuideDayDao;
import com.newview.bysj.domain.GuideDay;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("guideDayService")
public class GuideDayService extends BasicService<GuideDay, Integer> {

    GuideDayDao guideDayDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<GuideDay, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        guideDayDao = (GuideDayDao) basicDao;
    }

}
