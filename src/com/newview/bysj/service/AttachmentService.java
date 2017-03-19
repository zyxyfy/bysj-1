package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.AttachmentDao;
import com.newview.bysj.domain.Attachment;
import com.newview.bysj.jpaRepository.MyRepository;

/**
 * 附件的业务逻辑
 */
@Service("attachmentService")
public class AttachmentService extends BasicService<Attachment, Integer> {

    AttachmentDao attachmentDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Attachment, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        attachmentDao = (AttachmentDao) basicDao;
    }

}
