package com.newview.bysj.service;

import com.newview.bysj.dao.RoleDao;
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

@Service("roleService")
public class RoleService extends BasicService<Role, Integer> {
    RoleDao roleDao;

    @Autowired
    @Override
    public void setDasciDao(MyRepository<Role, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        roleDao = (RoleDao) basicDao;
    }

    @MethodDescription("获取用户的所有角色")
    public List<Role> getRoleByUser(User user) {
        List<Role> result = roleDao.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                ListJoin<Role, UserRole> depJoin = root.join(root.getModel().getList("userRole", UserRole.class), JoinType.LEFT);
                Predicate condition = cb.equal(depJoin.get("user").as(User.class), user);
                query.where(condition);
                return query.getRestriction();
            }
        });
        return result;
    }


    //分页获取角色
    public Page<Role> getRoleByPage(Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Role> rolePage = roleDao.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.isNotNull(root.get("id").as(Integer.class));
                criteriaQuery.where(predicate);
                return criteriaQuery.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.ASC, "id")));
        return rolePage;
    }

}
