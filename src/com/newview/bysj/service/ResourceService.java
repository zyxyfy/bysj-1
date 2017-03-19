package com.newview.bysj.service;

import com.newview.bysj.dao.ResourceDao;
import com.newview.bysj.domain.Resource;
import com.newview.bysj.domain.RoleResource;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service("resourceService")
public class ResourceService extends BasicService<Resource, Integer> {

    ResourceDao resourceDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Resource, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        resourceDao = (ResourceDao) basicDao;
    }

    @MethodDescription("通过角色获取对应的资源")
    public List<String> getResourceByRoleName(String roleName) {
        List<Resource> resources = resourceDao.findAll(new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //Predicate condition=cb.equal(root.get("roleResoures").get("role").get("roleName").as(String.class),roleName);
                ListJoin<Resource, RoleResource> depJoin = root.join(root.getModel().getList("roleResoures", RoleResource.class), JoinType.LEFT);
                Predicate condition = cb.equal(depJoin.get("role").get("roleName").as(String.class), roleName);
                query.where(condition);
                return query.getRestriction();
            }
        });
        List<String> urls = new ArrayList<String>();
        for (Resource resource : resources) {
            urls.add(resource.getUrl());
        }
        return urls;
    }

    @MethodDescription("获取所有的子菜单")
    public List<Resource> getChildResource() {
        List<Resource> resourceList = resourceDao.findAll(new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate condition1 = criteriaBuilder.isNotNull(root.get("parent").as(Resource.class));
                criteriaQuery.where(condition1);
                return criteriaQuery.getRestriction();
            }
        });
        return resourceList;
    }


    @MethodDescription("获取所有的父菜单")
    public List<Resource> getParentResource() {
        List<Resource> resourceList = resourceDao.findAll(new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate condition1 = criteriaBuilder.isNull(root.get("parent").as(Resource.class));
                criteriaQuery.where(condition1);
                return criteriaQuery.getRestriction();
            }
        });
        return resourceList;
    }
}
