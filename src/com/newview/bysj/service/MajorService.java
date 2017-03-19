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

import com.newview.bysj.dao.MajorDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Major;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("majorService")
public class MajorService extends BasicService<Major, Integer> {

    MajorDao majorDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Major, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        majorDao = (MajorDao) basicDao;
    }

    @MethodDescription("删除方法")
    public void delete(Major major) {
        if ((major.getStudentClass() == null || major.getStudentClass().size() == 0)
                &&
                (major.getGraduateProject() == null || major.getGraduateProject().size() == 0)) {
            majorDao.delete(major);
        }
    }

    @MethodDescription("根据指导教师得到同一教研室的第一个专业")
    public Major getTutorDefaultMajor(Tutor tutor) {
        Major major = majorDao.findOne(new Specification<Major>() {
            @Override
            public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("department").as(Department.class), tutor.getDepartment());
                query.where(condition);
                return query.getRestriction();
            }
        });
        return major;
    }

    //根据教研室获得专业
    public Page<Major> getMajorByDepartment(Department department, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Major> result = majorDao.findAll(new Specification<Major>() {
            @Override
            public Predicate toPredicate(Root<Major> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("department").as(Department.class), department);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "id")));
        return result;
    }

}
