package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateForDesignReviewerDao;
import com.newview.bysj.domain.RemarkTemplateForDesignReviewer;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateForDesignReviewerService")
public class RemarkTemplateForDesignReviewerService extends BasicService<RemarkTemplateForDesignReviewer, Integer> {

    RemarkTemplateForDesignReviewerDao remarkTemplateForDesignReviewerDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateForDesignReviewer, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateForDesignReviewerDao = (RemarkTemplateForDesignReviewerDao) basicDao;
    }

}
