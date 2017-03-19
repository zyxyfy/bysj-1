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

import com.newview.bysj.dao.SchoolExcellentProjectDao;
import com.newview.bysj.domain.SchoolExcellentProject;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("schoolExcellentProjectService")
public class SchoolExcellentProjectService extends BasicService<SchoolExcellentProject, Integer> {

    SchoolExcellentProjectDao schoolExcellentProjectDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<SchoolExcellentProject, Integer> basicDao) {
        this.basicDao = basicDao;
        schoolExcellentProjectDao = (SchoolExcellentProjectDao) basicDao;
    }

    @MethodDescription("获取被推荐为校优的课题")
    public Page<SchoolExcellentProject> getPagesSchoolExcellentProjectBySchoolAmin(String title, String tutorName, Boolean recommended, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<SchoolExcellentProject> result = schoolExcellentProjectDao.findAll(new Specification<SchoolExcellentProject>() {
            @Override
            public Predicate toPredicate(Root<SchoolExcellentProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (recommended != null) {
                    predicates.add(cb.equal(root.get("recommended").as(Boolean.class), recommended));
                }
                if (title != null || title != "") {
                    predicates.add(cb.like(root.get("graduateProject").get("title").as(String.class), "%" + title + "%"));
                }
                if (tutorName != null || tutorName != "") {
                    predicates.add(cb.like(root.get("graduateProject").get("proposer").get("name").as(String.class), "%" + tutorName + "%"));
                }
                predicates.add(cb.equal(root.get("graduateProject").get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;

    }

}
