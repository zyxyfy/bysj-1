package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateForPaperReviewerDao;
import com.newview.bysj.domain.RemarkTemplateForPaperReviewer;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateForPaperReviewerService")
public class RemarkTemplateForPaperReviewerService extends BasicService<RemarkTemplateForPaperReviewer, Integer> {

    RemarkTemplateForPaperReviewerDao remarkTemplateForPaperReviewerDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateForPaperReviewer, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateForPaperReviewerDao = (RemarkTemplateForPaperReviewerDao) basicDao;
    }

}
