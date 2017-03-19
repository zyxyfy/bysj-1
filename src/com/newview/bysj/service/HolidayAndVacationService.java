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

import com.newview.bysj.dao.HolidayAndVacationDao;
import com.newview.bysj.domain.HolidayAndVacation;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("holidayAndVacationService")
public class HolidayAndVacationService extends BasicService<HolidayAndVacation, Integer> {

    HolidayAndVacationDao holidayAndVacationDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<HolidayAndVacation, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        holidayAndVacationDao = (HolidayAndVacationDao) basicDao;
    }

    @MethodDescription(value = "")
    public Page<HolidayAndVacation> getPageByTutor(Integer pageNo, Integer pageSize, String description) {
        pageNo = (pageNo - 1) * pageSize;
        Page<HolidayAndVacation> result = holidayAndVacationDao.findAll(new Specification<HolidayAndVacation>() {
            @Override
            public Predicate toPredicate(Root<HolidayAndVacation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.like(root.get("description"), "%" + description + "%");
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

}
