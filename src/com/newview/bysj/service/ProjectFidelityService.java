package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ProjectFidelityDao;
import com.newview.bysj.domain.ProjectFidelity;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("projectFidelityService")
public class ProjectFidelityService extends BasicService<ProjectFidelity, Integer> {

    ProjectFidelityDao projectFidelityDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ProjectFidelity, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        projectFidelityDao = (ProjectFidelityDao) basicDao;
    }

}
