package com.newview.bysj.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.AuditDao;
import com.newview.bysj.dao.TaskDocDao;
import com.newview.bysj.domain.Audit;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.School;
import com.newview.bysj.domain.StudentClass;
import com.newview.bysj.domain.TaskDoc;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("taskDocService")
public class TaskDocService extends BasicService<TaskDoc, Integer> {

    TaskDocDao taskDocDao;
    @Autowired
    private GraduateProjectService graduateProjectSerivce;
    @Autowired
    private AuditDao auditDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<TaskDoc, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        taskDocDao = (TaskDocDao) basicDao;
    }

    @MethodDescription("删除任务书附件")
    public void setTaskDocAttachment(Integer taskDocId, HttpSession httpSession) {
        TaskDoc taskDoc = taskDocDao.findOne(taskDocId);
        if (taskDoc.getUrl() != null) {
            String realPath = httpSession.getServletContext().getRealPath("/");
            String path = realPath + "WEB-INF\\upload\\notice\\" + taskDoc.getUrl();
            File attachment = new File(path);
            if (attachment.exists()) {
                attachment.delete();
            }
        }
    }

    @MethodDescription("获取能被教研室主任审核的任务书集合")
    public List<TaskDoc> getAuditedTaskDocByDirector(Tutor tutor) {
        List<TaskDoc> result = taskDocDao.findAll(new Specification<TaskDoc>() {
            @Override
            public Predicate toPredicate(Root<TaskDoc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //所有本教研室学生的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("student").get("studentClass").get("major").get("department").as(Department.class), tutor.getDepartment()));
                //不能审核自己的任务书,主指导下达任务书
                predicates.add(cb.notEqual(root.get("graduateProject").get("mainTutorage").get("tutor"), tutor));
                //不能审核已审核过的任务书
                predicates.add(cb.isNull(root.get("auditByDepartmentDirector").as(Audit.class)));
                //当年的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        });
        return result;
    }

    /**
     * @param approved 如果为true代表查看审核通过的，如果为false代表查看未通过的，如果为null代表查看所有的
     */
    @MethodDescription("获取能被教研室主任审核的任务书")
    public Page<TaskDoc> getAuditedTaskDocByDirector(Tutor tutor, Boolean approved, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<TaskDoc> result = taskDocDao.findAll(new Specification<TaskDoc>() {
            @Override
            public Predicate toPredicate(Root<TaskDoc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //所有本教研室学生的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("student").get("studentClass").get("major").get("department").as(Department.class), tutor.getDepartment()));
                //不能审核自己的任务书,主指导下达任务书
                predicates.add(cb.notEqual(root.get("graduateProject").get("mainTutorage").get("tutor"), tutor));
                if (approved != null) {
                    predicates.add(cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), approved));
                }
                //当年的任务书
                //predicates.add(cb.equal(root.get("graduateProject").get("year").as(Integer.class),CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("获取能被院长审核的任务书")
    public List<TaskDoc> getAuditedTaskDoceByDean(Tutor tutor) {
        List<TaskDoc> result = taskDocDao.findAll(new Specification<TaskDoc>() {
            @Override
            public Predicate toPredicate(Root<TaskDoc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //所有本教研室学生的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("student").get("studentClass").get("major").get("department").as(Department.class), tutor.getDepartment()));
                //不能审核自己的任务书,主指导下达任务书
                predicates.add(cb.notEqual(root.get("graduateProject").get("mainTutorage").get("tutor"), tutor));
                //教研室主任审核通过，院长未审核的任务书
                predicates.add(cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), true));
                //当年的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        });
        return result;
    }

    /**
     * @param approved 如果为true查看院长审核通过的，如果为false查看院长审核未通过的，如果为null只能查看教研室主任审核通过的
     */
    @MethodDescription("获取能被院长审核的任务书")
    public Page<TaskDoc> getAuditedTaskDocByDean(Tutor tutor, Boolean approved, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<TaskDoc> result = taskDocDao.findAll(new Specification<TaskDoc>() {
            @Override
            public Predicate toPredicate(Root<TaskDoc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //所有本教研室学生的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("student").get("studentClass").get("major").get("department").as(Department.class), tutor.getDepartment()));
                //不能审核自己的任务书,主指导下达任务书
                predicates.add(cb.notEqual(root.get("graduateProject").get("mainTutorage").get("tutor"), tutor));
                if (approved != null) {
                    //教研室主任审核通过的任务书，院长时候通过根据approved
                    predicates.add(cb.and(cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), true), cb.equal(root.get("auditByBean").get("approve").as(Boolean.class), approved)));
                } else {
                    //院长只能看到教研室主任审核通过的任务书
                    predicates.add(cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), true));
                }
                //当年的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("同时拥有教研室主任和院长角色能够审核的任务书")
    public Page<TaskDoc> getAuditedTaskDocByDirectorAndDean(Tutor tutor, Boolean approved, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<TaskDoc> result = taskDocDao.findAll(new Specification<TaskDoc>() {
            @Override
            public Predicate toPredicate(Root<TaskDoc> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //所有本学院学生的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("student").get("studentClass").get("major").get("department").get("school").as(School.class), tutor.getDepartment().getSchool()));
                //不能审核自己的任务书,主指导下达任务书
                predicates.add(cb.notEqual(root.get("graduateProject").get("mainTutorage").get("tutor"), tutor));
                if (approved != null) {
                    if (approved) {
                        //审核教研室主任审核通过的任务书，院长是否通过根据approved
                        predicates.add(cb.and(cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), true), cb.equal(root.get("auditByBean").get("approve").as(Boolean.class), true)));
                    } else {
                        //审核教研室主任审核通过的任务书，院长是否通过根据approved
                        predicates.add(cb.or(cb.equal(root.get("auditByDepartmentDirector").get("approve").as(Boolean.class), false), cb.equal(root.get("auditByBean").get("approve").as(Boolean.class), false)));
                    }
                }
                //当年的任务书
                predicates.add(cb.equal(root.get("graduateProject").get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("删除任务书")
    public Boolean deleteTaskDoc(Integer taskDocId, HttpSession httpSession) {
        //获取任务书
        System.out.println("taskDocId==========" + taskDocId);
        TaskDoc deletedTaskDoc = this.findById(taskDocId);
        if (deletedTaskDoc == null) {
            throw new MessageException("不存在该任务书");
        }
        //取消和graduateproject的关联
        GraduateProject graduateProject = deletedTaskDoc.getGraduateProject();
        graduateProject.setTaskDoc(null);
        deletedTaskDoc.setGraduateProject(null);
        graduateProjectSerivce.update(graduateProject);
        graduateProjectSerivce.save(graduateProject);
        //删除附件
        CommonHelper.delete(httpSession, deletedTaskDoc.getUrl());
        //删除任务书
        taskDocDao.delete(deletedTaskDoc);
        return true;
    }
}
