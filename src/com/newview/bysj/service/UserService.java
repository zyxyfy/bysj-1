package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.UserDao;
import com.newview.bysj.domain.User;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("userService")
public class UserService extends BasicService<User, Integer> {
    UserDao userDao;

    @Autowired
    @Override
    public void setDasciDao(MyRepository<User, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        userDao = (UserDao) basicDao;
    }

}
