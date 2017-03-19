package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateForPaperGroupDao;
import com.newview.bysj.domain.RemarkTemplateForPaperGroup;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateForPaperGroupService")
public class RemarkTemplateForPaperGroupService extends BasicService<RemarkTemplateForPaperGroup, Integer> {

    RemarkTemplateForPaperGroupDao remarkTemplateForPaperGroupDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateForPaperGroup, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateForPaperGroupDao = (RemarkTemplateForPaperGroupDao) basicDao;
    }

}
