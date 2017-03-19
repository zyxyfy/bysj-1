package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateForPaperTutorDao;
import com.newview.bysj.domain.RemarkTemplateForPaperTutor;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateForPaperTutorService")
public class RemarkTemplateForPaperTutorService extends BasicService<RemarkTemplateForPaperTutor, Integer> {

    RemarkTemplateForPaperTutorDao remarkTemplateForPaperTutorDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateForPaperTutor, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateForPaperTutorDao = (RemarkTemplateForPaperTutorDao) basicDao;
    }

}
