package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ConstraintOfProposeProjectDao;
import com.newview.bysj.domain.ConstraintOfProposeProject;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("constraintOfProposeProjectService")
public class ConstraintOfProposeProjectService extends BasicService<ConstraintOfProposeProject, Integer> {

    ConstraintOfProposeProjectDao constraintOfProposeProjectDao;

    @Override
    @Autowired
    public void setDasciDao(
            MyRepository<ConstraintOfProposeProject, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        constraintOfProposeProjectDao = (ConstraintOfProposeProjectDao) basicDao;
    }

    @MethodDescription("处理tutor所在教研室是否在'维护题目'的时间段内")
    public Boolean isAbleToUpdateProject(Tutor tutor) {
        //如果增加题目的时间已经进行设置
        ConstraintOfProposeProject constraintOfProposeProject = this.uniqueResult("department", Department.class, tutor.getDepartment());
        if (constraintOfProposeProject != null) {
            //如果当前时间在增加题目时间的起止时间之间，isAbleToEditProject则为真
            return CommonHelper.isNowInPeroid(constraintOfProposeProject.getStartTime(), constraintOfProposeProject.getEndTime());
        } else
            //增加题目时间未进行设置，isAbleToEditProject为真
            return true;
    }

}
