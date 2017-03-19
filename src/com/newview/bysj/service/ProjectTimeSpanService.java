package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ProjectTimeSpanDao;
import com.newview.bysj.domain.ProjectTimeSpan;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("projectTimeSpanService")
public class ProjectTimeSpanService extends BasicService<ProjectTimeSpan, Integer> {

    ProjectTimeSpanDao projectTimeSpanDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ProjectTimeSpan, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        projectTimeSpanDao = (ProjectTimeSpanDao) basicDao;
    }

}
