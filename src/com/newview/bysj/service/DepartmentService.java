package com.newview.bysj.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.newview.bysj.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.DepartmentDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.School;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

import java.util.List;

@Service("departmentService")
public class DepartmentService extends BasicService<Department, Integer> {

    DepartmentDao departmentDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Department, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        departmentDao = (DepartmentDao) basicDao;

    }

    @MethodDescription("删除方法")
    public void deleteDepartment(Department department) {
        if (this.hasChild(department)) {
            return;
        } else {
            departmentDao.delete(department);
        }

    }

    public Boolean hasChild(Department department) {
        if (department.getMajor() == null || department.getMajor().size() == 0) {
            return false;
        }
        return true;
    }

    //根据学院获取department
    public Page<Department> getDepartmentBySchool(School school, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Department> result = departmentDao.findAll(new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("school").as(School.class), school);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    //不用分页获取学院下的所有教研室
    public List<Department> getDepartmentBySchoolNoPage(School school) {
        List<Department> departmentList = departmentDao.findAll(new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("school").as(School.class), school);
                criteriaQuery.where(predicate);
                return criteriaQuery.getRestriction();
            }
        });
        return departmentList;
    }
}
