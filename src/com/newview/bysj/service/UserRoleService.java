package com.newview.bysj.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.RoleDao;
import com.newview.bysj.dao.UserRoleDao;
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("userRoleService")
public class UserRoleService extends BasicService<UserRole, Integer> {
    UserRoleDao userRoleDao;
    @Autowired
    RoleDao roleDao;

    @Autowired
    @Override
    public void setDasciDao(MyRepository<UserRole, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        userRoleDao = (UserRoleDao) basicDao;

    }

    @MethodDescription("判断该用户是否有对应的角色，没有则添加")
    public boolean insertRole(User user, Role role) {
        List<Role> roles = roleDao.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                ListJoin<Role, UserRole> depJoin = root.join(root.getModel().getList("userRole", UserRole.class), JoinType.LEFT);
                Predicate condition = cb.equal(depJoin.get("user").as(User.class), user);
                query.where(condition);
                return query.getRestriction();
            }
        });
        if (!roles.contains(role)) {
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRoleDao.save(userRole);
        }
        return true;
    }

}
