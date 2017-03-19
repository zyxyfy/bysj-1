package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ConstraintOfApproveOpenningReportDao;
import com.newview.bysj.domain.ConstraintOfApproveOpenningReport;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("constraintOfApproveOpenningReportService")
public class ConstraintOfApproveOpenningReportService extends BasicService<ConstraintOfApproveOpenningReport, Integer> {

    ConstraintOfApproveOpenningReportDao constraintOfApproveOpenningReportDao;

    @Override
    @Autowired
    public void setDasciDao(
            MyRepository<ConstraintOfApproveOpenningReport, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        constraintOfApproveOpenningReportDao = (ConstraintOfApproveOpenningReportDao) basicDao;

    }

}
