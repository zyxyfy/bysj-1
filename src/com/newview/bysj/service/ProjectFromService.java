package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ProjectFromDao;
import com.newview.bysj.domain.ProjectFrom;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("projectFromService")
public class ProjectFromService extends BasicService<ProjectFrom, Integer> {

    ProjectFromDao projectFromDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ProjectFrom, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        projectFromDao = (ProjectFromDao) basicDao;
    }
}
