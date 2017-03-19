package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateForDesignGroupDao;
import com.newview.bysj.domain.RemarkTemplateForDesignGroup;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateForDesignGroupService")
public class RemarkTemplateForDesignGroupService extends BasicService<RemarkTemplateForDesignGroup, Integer> {

    RemarkTemplateForDesignGroupDao remarkTemplateForDesignGroupDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateForDesignGroup, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateForDesignGroupDao = (RemarkTemplateForDesignGroupDao) basicDao;
    }

}
