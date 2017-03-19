package com.newview.bysj.service;

import com.newview.bysj.dao.PaperProjectDao;
import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service("paperProjectService")
public class PaperProjectService extends BasicService<PaperProject, Integer> {

    PaperProjectDao paperProjectDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<PaperProject, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        paperProjectDao = (PaperProjectDao) basicDao;
    }

    @MethodDescription("查询论文-通过课题提交人")
    public Page<PaperProject> getPaperProjectByProposer(Tutor proposer, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //通过课题提交人查询
                Predicate c1 = cb.equal(root.get("proposer").as(Tutor.class), proposer);
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("查询论文-通过课题主指导")
    public Page<PaperProject> getPaperProjectByMainTutorage(Tutor mainTutorage, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("mainTutorage").get("tutor").as(Tutor.class), mainTutorage);
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                //教研室审核通过的课题
                Predicate c3 = cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true);
                query.where(cb.and(c1, c2, c3));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;

    }

    @MethodDescription("查询论文-获取教研室下所有课题")
    public Page<PaperProject> getPaperProjectByDepartment(Department department, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //教研室下的所有课题
                Predicate c1 = cb.equal(root.get("major").get("department").as(Department.class), department);
                //开题报告不能为空
                Predicate c2 = cb.isNotNull(root.get("openningReport").as(OpenningReport.class));
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2, c3));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    /**
     * 对于同是院长和指导老师角色的对象需要用该方法
     */
    @MethodDescription("查询论文-同时拥有教研室主任和指导教师角色")
    public Page<PaperProject> getPaperProjectByMainTutorageAndDepartment(Tutor mainTutorage, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //教研室下的所有课题
                Predicate c1 = cb.equal(root.get("major").get("department").as(Department.class), mainTutorage.getDepartment());
                //指导教师的课题
                Predicate c2 = cb.equal(root.get("mainTutorage").get("tutor").as(Tutor.class), mainTutorage);
                //所在教研室的所有课题或者是作为主指导
                Predicate c3 = cb.or(c1, c2);
                Predicate c4 = cb.isNotNull(root.get("openningReport").as(OpenningReport.class));
                Predicate c5 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c3, c4, c5));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("查询论文-通过学院")
    public Page<PaperProject> getPaperProjectBySchool(School school, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //学院下的所有课题
                Predicate c1 = cb.equal(root.get("major").get("department").get("school").as(School.class), school);
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                Predicate c3 = cb.isNotNull(root.get("openningReport").as(OpenningReport.class));
                query.where(cb.and(c1, c2, c3));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("查询论文-通过学院")
    public Page<PaperProject> getPaperProjectBySchoolAndTitle(School school, String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //学院下的所有课题
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

    @MethodDescription("查询所有论文")
    public Page<PaperProject> getPaperProjectByTitle(String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

    @MethodDescription("得到当前用户教研室下老师所有的课题")
    public Page<PaperProject> getPaperProjectByDepartment(Tutor tutor, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<PaperProject> result = paperProjectDao.findAll(new Specification<PaperProject>() {
            @Override
            public Predicate toPredicate(Root<PaperProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
