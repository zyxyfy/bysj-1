package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateItemsOptionDao;
import com.newview.bysj.domain.RemarkTemplateItemsOption;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateItemsOptionService")
public class RemarkTemplateItemsOptionService extends BasicService<RemarkTemplateItemsOption, Integer> {

    RemarkTemplateItemsOptionDao remarkTemplateItemsOptionDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateItemsOption, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateItemsOptionDao = (RemarkTemplateItemsOptionDao) basicDao;
    }

}
