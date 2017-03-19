package com.newview.bysj.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.DesignProjectDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.DesignProject;
import com.newview.bysj.domain.School;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("designProjectService")
public class DesignProjectService extends BasicService<DesignProject, Integer> {

    DesignProjectDao designProjectDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<DesignProject, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        designProjectDao = (DesignProjectDao) basicDao;
    }

    @MethodDescription("通过发布者获取毕业设计")
    public Page<DesignProject> getDesignProjectByProposer(Tutor proposer, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<DesignProject> result = designProjectDao.findAll(new Specification<DesignProject>() {
            @Override
            public Predicate toPredicate(Root<DesignProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("proposer").as(Tutor.class), proposer);
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("查询设计-通过学院")
    public Page<DesignProject> getDesignProjectBySchoolAndTitle(School school, String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<DesignProject> result = designProjectDao.findAll(new Specification<DesignProject>() {
            @Override
            public Predicate toPredicate(Root<DesignProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //根据学院筛选
                predicates.add(cb.equal(root.get("major").get("department").get("school").as(School.class), school));
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                //已经送审的才能进行审核
                predicates.add(cb.isNotNull(root.get("proposerSubmitForApproval").as(Boolean.class)));
                //学生已选择的课题
                predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                //教研室审核通过的课题
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("查询所有设计")
    public Page<DesignProject> getDesignProjectByTitle(String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<DesignProject> result = designProjectDao.findAll(new Specification<DesignProject>() {
            @Override
            public Predicate toPredicate(Root<DesignProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                //已经送审的才能进行审核
                predicates.add(cb.isNotNull(root.get("proposerSubmitForApproval").as(Boolean.class)));
                //学生已选择的课题
                predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                //教研室审核通过的课题
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("得到当前用户教研室下所有老师的题目")
    public Page<DesignProject> getDesignProjectByDepartment(Tutor tutor, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<DesignProject> result = designProjectDao.findAll(new Specification<DesignProject>() {
            @Override
            public Predicate toPredicate(Root<DesignProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("major").get("department").as(Department.class), tutor.getDepartment());
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2));
                query.orderBy(cb.desc(root.get("proposer").get("id").as(Integer.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

}
