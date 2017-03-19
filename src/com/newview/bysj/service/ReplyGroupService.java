package com.newview.bysj.service;

import com.newview.bysj.dao.ReplyGroupDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.domain.School;
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

@Service("replyGroupService")
public class ReplyGroupService extends BasicService<ReplyGroup, Integer> {

    ReplyGroupDao replyGroupDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ReplyGroup, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        replyGroupDao = (ReplyGroupDao) basicDao;
    }

    @MethodDescription("删除答辩小组")
    public void delete(Integer id) {
        ReplyGroup replyGroup = this.findById(id);
        //这样删除才能只删除中间表
        replyGroup.setMembers(null);
        replyGroupDao.delete(replyGroup);
    }

    @MethodDescription("通过教研室查询答辩小组")
    public Page<ReplyGroup> getReplyGroupByDepartment(Department department, String name, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<ReplyGroup> result = replyGroupDao.findAll(new Specification<ReplyGroup>() {
            @Override
            public Predicate toPredicate(Root<ReplyGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("department").as(Department.class), department));
                //对答辩小组模糊查询
                if (name != null) {
                    predicates.add(cb.like(root.get("description").as(String.class), "%" + name + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("通过教研室和名称查询答辩小组")
    public Page<ReplyGroup> getReplyGroupByDepartmentAndName(Department department, String name, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<ReplyGroup> result = replyGroupDao.findAll(new Specification<ReplyGroup>() {
            @Override
            public Predicate toPredicate(Root<ReplyGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("department").as(Department.class), department));
                if (name != null) {
                    predicates.add(cb.equal(root.get("description"), "%" + name + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("通过学院获取答辩小组")
    public Page<ReplyGroup> getReplyGroupSchool(School school, String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<ReplyGroup> result = replyGroupDao.findAll(new Specification<ReplyGroup>() {
            @Override
            public Predicate toPredicate(Root<ReplyGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("department").get("school").as(School.class), school));
                if (title != null) {
                    predicates.add(cb.equal(root.get("description"), "%" + title + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    //得到num字段的最大值
    public Integer getNum() {
        return replyGroupDao.getNum();
    }

}
