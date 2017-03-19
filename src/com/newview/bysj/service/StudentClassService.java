package com.newview.bysj.service;

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

import com.newview.bysj.dao.StudentClassDao;
import com.newview.bysj.domain.Major;
import com.newview.bysj.domain.StudentClass;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("studentClassService")
public class StudentClassService extends BasicService<StudentClass, Integer> {

    StudentClassDao studentClassDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<StudentClass, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        studentClassDao = (StudentClassDao) basicDao;
    }

    public void delete(StudentClass studentClass) {
        if (studentClass.getStudent().size() == 0) {
            studentClassDao.delete(studentClass);
        }
    }

    //根据专业获取班级
    public Page<StudentClass> getStudentClassByMajor(Major major, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<StudentClass> result = studentClassDao.findAll(new Specification<StudentClass>() {
            @Override
            public Predicate toPredicate(Root<StudentClass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("major").as(Major.class), major);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "id")));
        return result;
    }

}
