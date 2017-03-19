package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateItemsDao;
import com.newview.bysj.domain.RemarkTemplateItems;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("remarkTemplateItemsService")
public class RemarkTemplateItemsService extends BasicService<RemarkTemplateItems, Integer> {

    RemarkTemplateItemsDao remarkTemplateItemsDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplateItems, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateItemsDao = (RemarkTemplateItemsDao) basicDao;
    }

}
