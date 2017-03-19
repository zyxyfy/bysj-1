package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.newview.bysj.dao.AuditDao;
import com.newview.bysj.domain.Audit;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;

/**
 * 审核的业务逻辑
 */
@Service("auditService")
public class AuditService extends BasicService<Audit, Integer> {

    AuditDao auditDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Audit, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        auditDao = (AuditDao) basicDao;
    }

    public Audit saveNewAudit() {
        Audit audit = new Audit();
        audit.setApprove(true);
        audit.setAuditDate(CommonHelper.getNow());
        return audit;
    }
}
