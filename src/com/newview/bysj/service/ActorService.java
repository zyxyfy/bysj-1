package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ActorDao;
import com.newview.bysj.domain.Actor;
import com.newview.bysj.jpaRepository.MyRepository;

/**
 * 所有用户的业务逻辑
 */
@Service("actorService")
public class ActorService extends BasicService<Actor, Integer> {
    ActorDao actorDao;

    @Autowired
    @Override
    public void setDasciDao(MyRepository<Actor, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        this.actorDao = (ActorDao) basicDao;
    }


}
