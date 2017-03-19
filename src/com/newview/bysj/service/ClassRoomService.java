package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ClassRoomDao;
import com.newview.bysj.domain.ClassRoom;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("classRoomService")
public class ClassRoomService extends BasicService<ClassRoom, Integer> {

    ClassRoomDao classRoomDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ClassRoom, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        classRoomDao = (ClassRoomDao) basicDao;
    }

}
