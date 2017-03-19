package com.newview.bysj.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RemarkTemplateDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.RemarkTemplate;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("remarkTemplateService")
public class RemarkTemplateService extends BasicService<RemarkTemplate, Integer> {

    RemarkTemplateDao remarkTemplateDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<RemarkTemplate, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        remarkTemplateDao = (RemarkTemplateDao) basicDao;
    }

    @MethodDescription("获取教研室主任所在教研室的所有评论模板")
    public Page<RemarkTemplate> getPageByDirectior(Tutor tutor, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<RemarkTemplate> result = remarkTemplateDao.findAll(new Specification<RemarkTemplate>() {
            @Override
            public Predicate toPredicate(Root<RemarkTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("department").as(Department.class), tutor.getDepartment());
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

}
