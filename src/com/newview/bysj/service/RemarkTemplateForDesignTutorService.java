package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateForDesignTutorDao;
import com.newview.bysj.domain.RemarkTemplateForDesignTutor;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateForDesignTutorService")
public class RemarkTemplateForDesignTutorService extends BasicService<RemarkTemplateForDesignTutor, Integer> {

    RemarkTemplateForDesignTutorDao remarkTemplateForDesignTutorDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateForDesignTutor, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateForDesignTutorDao = (RemarkTemplateForDesignTutorDao) basicDao;
    }

}
