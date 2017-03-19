package com.newview.bysj.service;

import com.newview.bysj.dao.OpenningReportDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.OpenningReport;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service("openningReportService")
public class OpenningReportService extends BasicService<OpenningReport, Integer> {


    OpenningReportDao openningReportDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<OpenningReport, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        openningReportDao = (OpenningReportDao) basicDao;
    }


    /**
     * @param approved 如果是true查看审核通过的，如果为false查看审核未通过的，如果为null查看全部
     */
    @MethodDescription("通过主指导教师获取能够审核的开题报告")
    public Page<OpenningReport> getAuditedOpenningReportByTutor(Tutor tutor, Boolean approved, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<OpenningReport> result = openningReportDao.findAll(new Specification<OpenningReport>() {
            @Override
            public Predicate toPredicate(Root<OpenningReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //课题的主指导是tutor的开题报告
                predicates.add(cb.equal(root.get("paperProject").get("mainTutorage").get("tutor").as(Tutor.class), tutor));
                //当年的报题
                predicates.add(cb.equal(root.get("paperProject").get("year"), CommonHelper.getYear()));
                if (approved != null) {
                    predicates.add(cb.equal(root.get("auditByTutor").get("approve").as(Boolean.class), approved));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("通过教研室主任获取能够审核的开题报告")
    public Page<OpenningReport> getAuditedOpenningReportByDirector(Tutor tutor, Boolean approved, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<OpenningReport> result = openningReportDao.findAll(new Specification<OpenningReport>() {
            @Override
            public Predicate toPredicate(Root<OpenningReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获取本教研室所有学生的开题报告
                predicates.add(cb.equal(root.get("paperProject").get("student").get("studentClass").get("major").get("department").as(Department.class), tutor.getDepartment()));
                //当年的报告
                predicates.add(cb.equal(root.get("paperProject").get("year").as(Integer.class), CommonHelper.getYear()));
                if (approved != null) {
                    //指导教师审核通过，教研室主任审核通过否看approved
                    predicates.add(cb.and(cb.equal(root.get("auditByTutor").get("approve").as(Boolean.class), true), cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), approved)));
                } else {
                    //只能查看指导教师审核通过的开题报告
                    predicates.add(cb.equal(root.get("auditByTutor").get("approve").as(Boolean.class), true));
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("同时拥有主指导和教研室主任角色可以审核的开题报告")
    public Page<OpenningReport> getAuditOpenningReportByTutorAndDirector(Tutor tutor, Boolean approved, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<OpenningReport> result = openningReportDao.findAll(new Specification<OpenningReport>() {
            @Override
            public Predicate toPredicate(Root<OpenningReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获取本教研室所有学生的开题报告
                predicates.add(cb.equal(root.get("paperProject").get("student").get("studentClass").get("major").get("department").as(Department.class), tutor.getDepartment()));
                //当年的报告
                predicates.add(cb.equal(root.get("paperProject").get("year").as(Integer.class), CommonHelper.getYear()));
                if (approved != null) {
                    if (approved) {
                        predicates.add(cb.and(cb.equal(root.get("auditByTutor").get("approve").as(Boolean.class), true), cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), true)));
                    } else {
                        predicates.add(cb.or(cb.equal(root.get("auditByTutor").get("approve").as(Boolean.class), false), cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), false)));
                    }
                }
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

}
