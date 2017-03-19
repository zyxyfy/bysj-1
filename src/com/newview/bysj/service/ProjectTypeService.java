package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ProjectTypeDao;
import com.newview.bysj.domain.ProjectType;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("projectTypeService")
public class ProjectTypeService extends BasicService<ProjectType, Integer> {

    ProjectTypeDao projectTypeDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ProjectType, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        projectTypeDao = (ProjectTypeDao) basicDao;
    }

}
