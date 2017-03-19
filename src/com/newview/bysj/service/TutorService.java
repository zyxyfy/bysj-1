package com.newview.bysj.service;

import com.newview.bysj.dao.TutorDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

@Service("tutorService")
public class TutorService extends BasicService<Tutor, Integer> {

    TutorDao tutorDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Tutor, Integer> basicDao) {
        this.basicDao = basicDao;
        tutorDao = (TutorDao) basicDao;
    }

    @MethodDescription("获得某个指导教师的教研室主任")
    public Tutor getDirector(Tutor tutor) {
        Tutor result = tutorDao.findOne(new Specification<Tutor>() {
            @Override
            public Predicate toPredicate(Root<Tutor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("department").as(Department.class), tutor.getDepartment());
                Join<Tutor, User> depJoin = root.join("user", JoinType.LEFT);
                Join<User, UserRole> listJoin = depJoin.join("userRole", JoinType.LEFT);
                Predicate c2 = cb.equal(listJoin.get("role").get("roleName").as(String.class), "ROLE_DEPARTMENT_DIRECTOR");
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        });
        return result;
    }

    public Page<Tutor> getDirectors(Tutor tutor) {
        Page<Tutor> result = tutorDao.findAll(new Specification<Tutor>() {
            @Override

            public Predicate toPredicate(Root<Tutor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("department").as(Department.class), tutor.getDepartment());
                Join<Tutor, User> depJoin = root.join("user", JoinType.LEFT);
                Join<User, UserRole> listJoin = depJoin.join("userRole", JoinType.LEFT);
                Predicate c2 = cb.equal(listJoin.get("role").get("roleName").as(String.class), "ROLE_DEPARTMENT_DIRECTOR");
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        }, new PageRequest(1, 10));
        return result;
    }

}
