package com.newview.bysj.service;

import com.newview.bysj.dao.SchoolSupervisionReportDao;
import com.newview.bysj.domain.School;
import com.newview.bysj.domain.SchoolSupervisionReport;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.other.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 提交督导报告的service
 * Created by zhan on 2016/4/23.
 */
@Service("schoolSupervisionReportService")
public class SchoolSupervisionReportService extends BasicService<SchoolSupervisionReport, Integer> {
    SchoolSupervisionReportDao schoolSupervisionReportDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<SchoolSupervisionReport, Integer> basicDao) {
        this.basicDao = basicDao;
        this.schoolSupervisionReportDao = (SchoolSupervisionReportDao) basicDao;
    }


    public Page<SchoolSupervisionReport> getPageByTutor(Tutor tutor, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<SchoolSupervisionReport> schoolSupervisionReportPage = schoolSupervisionReportDao.findAll(new Specification<SchoolSupervisionReport>() {
            @Override
            public Predicate toPredicate(Root<SchoolSupervisionReport> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate condition = criteriaBuilder.equal(root.get("school").as(School.class), tutor.getDepartment().getSchool());
                criteriaQuery.where(condition);
                return criteriaQuery.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return schoolSupervisionReportPage;
    }
}
