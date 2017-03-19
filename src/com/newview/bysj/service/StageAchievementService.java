package com.newview.bysj.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.StageAchievementDao;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.StageAchievement;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("stageAchievementService")
public class StageAchievementService extends BasicService<StageAchievement, Integer> {

    StageAchievementDao stageAchievementDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<StageAchievement, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        stageAchievementDao = (StageAchievementDao) basicDao;
    }

    /*@MethodDescription("根据tutor作为主指导和合作指导来获取阶段成果")
    public Page<StageAchievement> getPageByGraduateProjects(Integer pageNo,Integer pageSize) {
        pageNo=CommonHelper.getPageNo(pageNo, pageSize);
        pageSize=CommonHelper.getPageSize(pageSize);
        Page<StageAchievement> result = stageAchievementDao.findAll(new Specification<StageAchievement>() {
            @Override
            public Predicate toPredicate(Root<StageAchievement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //Predicate p = cb.and(root.in(root.get("graduateProject").as(GraduateProject.class), graduateProjects));
                Predicate p =cb.in(root.get("graduateProject").as(GraduateProject.class));
                query.where(p);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }*/
    public Page<StageAchievement> getPageByGraduateProjects(GraduateProject graduateProject, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<StageAchievement> result = stageAchievementDao.findAll(new Specification<StageAchievement>() {
            @Override
            public Predicate toPredicate(Root<StageAchievement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("graduateProject").as(GraduateProject.class), graduateProject);
                query.where(condition);
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize));
        return result;
    }
}
