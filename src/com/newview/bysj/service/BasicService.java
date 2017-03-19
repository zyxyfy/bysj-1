package com.newview.bysj.service;

import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import com.newview.bysj.other.PageCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * 基本的业务逻辑
 *
 * @param <T>
 * @param <PK>
 */
@Service("basicService")
public abstract class BasicService<T extends Serializable, PK extends Serializable> {

    MyRepository<T, PK> basicDao;

    public abstract void setDasciDao(MyRepository<T, PK> basicDao);


    /**
     * 保存po
     *
     * @param entity 要保存的实体
     */
    @MethodDescription("保存po")
    public void save(T entity) {
        basicDao.save(entity);

    }


    /**
     * 删除po
     *
     * @param entity 要删除的实体
     */
    @MethodDescription("删除po")
    public void deleteObject(T entity) {
        if (entity != null) {
            basicDao.delete(entity);
        }
    }


    /**
     * 根据id删除po
     *
     * @param id 要删除的实体的id
     */
    @MethodDescription("根据id删除po")
    public void deleteById(PK id) {
        basicDao.delete(id);
    }


    /**
     * 删除所有
     */
    @MethodDescription("删除所有")
    public void delete() {
        basicDao.deleteAll();
    }


    /**
     * 更新po
     *
     * @param entity 要更新的实体
     */
    @MethodDescription("更新po")
    public void update(T entity) {
        basicDao.merge(entity);
    }


    /**
     * 保存或更新实体，在controller层建议使用此方法
     *
     * @param entity 要保存或更新的实体
     */
    @MethodDescription("保存或更新")
    public void saveOrUpdate(T entity) {
        basicDao.saveAndFlush(entity);
    }


    /**
     * 根据id获取po
     *
     * @param id 要获取的实体的id
     * @return 实体
     */
    @MethodDescription("根据id获取po")
    public T findById(PK id) {
        return basicDao.findOne(id);
    }


    /**
     * 根据id判断po是否存在
     *
     * @param id 需要判断的id
     * @return 如果存在则返回true, 否则返回false
     */
    @MethodDescription("根据id判断po是否存在")
    public boolean exists(PK id) {
        return findById(id) != null;
    }


    /**
     * 获取所有的po
     *
     * @return 当前所有实体对应的list集合
     */
    @MethodDescription("获取所有的po")
    public List<T> findAll() {
        return basicDao.findAll();
    }

    /**
     * 获取当前实体的数量
     *
     * @param entityClass 需要获取的实体的类型，Class对象
     * @return 实体的个数
     */
    public Integer countAll(Class<T> entityClass) {
        return basicDao.count(entityClass);
    }


    /**
     * 按属性查找对象列表，匹配方式为模糊
     *
     * @param propertyName  要查找的属性名
     * @param propertyValue 当前属性名对应的属性值
     * @return 满足此条件的所有对象的list集合
     */
    @MethodDescription("按属性查找对象列表，匹配方式为模糊")
    public List<T> list(String propertyName, String propertyValue) {
        List<T> results = basicDao.findAll(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.like(root.get(propertyName).as(String.class), "%" + propertyValue + "%");
                query.where(condition);
                return query.getRestriction();
            }

        });
        return results;
    }


    /**
     * 按属性查找唯一此条件的对象,关联的实体不作为属性时用，匹配方式为相等
     * 如果按此条件查找的对象不唯一，则此方法会抛出异常，建议调用此方法时增加异常处理
     *
     * @param propertyName  要查找的属性名
     * @param propertyValue 当前属性名对应的属性值
     * @return 满足此条件的唯一对象
     */
    @MethodDescription("按属性查找对象列表,关联的实体不作为属性时用，匹配方式为相等")
    public T uniqueResult(String propertyName, Object propertyValue) {
        T result = basicDao.findOne(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get(propertyName).as(String.class), propertyValue);
                query.where(condition);
                return query.getRestriction();
            }

        });
        return result;
    }


    /**
     * 按属性查找对象列表,关联的实体作为属性时用，匹配方式为相等
     * 如果按此条件查找的对象不唯一，则此方法会抛出异常，建议调用此方法时增加异常处理
     *
     * @param propertyName  需要查找的属性名
     * @param propertyClass 该属性对应的Class对象
     * @param propertyValue 当前属性名对应的属性值
     * @return 满足此条件的唯一对象
     */
    @MethodDescription("按属性查找对象列表,关联的实体作为属性时用，匹配方式为相等")
    public T uniqueResult(String propertyName, Class propertyClass, Object propertyValue) {
        T result = basicDao.findOne(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                @SuppressWarnings("unchecked")
                Predicate condition = cb.equal(root.get(propertyName).as(propertyClass), propertyValue);
                query.where(condition);
                return query.getRestriction();
            }

        });
        return result;
    }

    /*
     * 按外键查找对象列表，匹配方式为相等
     * @param propertyName
     * @param propertyValue
     * return
     */
    @MethodDescription("按外键查找对象列表，匹配方式为相等")
    public List<T> list(String propertyName, Class entityClass, Object propertyValue) {
        System.out.println("已跳入service层的方法");
        List<T> list = basicDao.findAll(new Specification<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get(propertyName).as(entityClass), propertyValue);
                query.where(condition);
                return query.getRestriction();
            }

        });
        System.out.println("已跳出service层方法");
        return list;
    }

    /*
     * 分页查询
     * pageNo当前页页码
     * pageSize每页显示的数据条数
     */
    @MethodDescription("分页查询")
    public Page<T> pageQuery(Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<T> list = basicDao.findAll(new PageRequest(pageNo, pageSize));
        return list;
    }

    /*
     * 分页查询
     * 可以按某个属性进行排序
     */
    @MethodDescription("分页查询")
    public Page<T> pageQuery(Integer pageNo, Integer pageSize, Direction orderDirection, String orderValue) {
        //pageNo=(pageNo-1)*pageSize;
        Page<T> list = basicDao.findAll(new PageRequest(pageNo, pageSize, new Sort(orderDirection, orderValue)));
        return list;
    }

    /*
     * 分页条件查询，匹配方式为模糊
     * 可根据某一项进行排序，默认按id进行排序
     * 该查询方法只用于有description属性的实体类
     */
    public Page<T> pageQuery(Integer pageNo, Integer pageSize, String description) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<T> result = basicDao.findAll(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.like(root.get("description").as(String.class), "%" + description + "%");
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;

    }

    //获取某个字段的所有值
    public List<Object[]> findByProperty(Class entityClass, String property) {
        return basicDao.find(entityClass, property);
    }
}
