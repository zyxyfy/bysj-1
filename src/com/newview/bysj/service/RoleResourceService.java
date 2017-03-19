package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RoleResourceDao;
import com.newview.bysj.domain.RoleResource;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("roleResourceService")
public class RoleResourceService extends BasicService<RoleResource, Integer> {

    RoleResourceDao roleResourceDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RoleResource, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        roleResourceDao = (RoleResourceDao) basicDao;
    }

}
