package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.TutorAsMemberToReplyGroupDao;
import com.newview.bysj.domain.TutorAsMemberToReplyGroup;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("tutorAsMemberToReplyGroupService")
public class TutorAsMemberToReplyGroupService extends BasicService<TutorAsMemberToReplyGroup, Integer> {

    TutorAsMemberToReplyGroupDao tutorAsMemberToReplyGroupDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<TutorAsMemberToReplyGroup, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        tutorAsMemberToReplyGroupDao = (TutorAsMemberToReplyGroupDao) basicDao;
    }

}
