package com.newview.bysj.service;

import com.newview.bysj.dao.MailAddressesDao;
import com.newview.bysj.dao.MailDao;
import com.newview.bysj.domain.Actor;
import com.newview.bysj.domain.Mail;
import com.newview.bysj.domain.MailAddresses;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import com.newview.bysj.other.PageCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service("mailAddressesService")
public class MailAddressesService extends BasicService<MailAddresses, Integer> {

    MailAddressesDao mailAddressesDao;
    @Autowired
    MailDao mailDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<MailAddresses, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        mailAddressesDao = (MailAddressesDao) basicDao;
    }

    @MethodDescription("获取用户收到的通知")
    public Page<MailAddresses> getPageByAddresse(Actor addresses, PageCondition pageCondition) {
        Integer pageNo = CommonHelper.getPageNo(pageCondition.getPageNow(), pageCondition.getPageSize());
        Integer pageSize = CommonHelper.getPageSize(pageCondition.getPageSize());
        Page<MailAddresses> result = mailAddressesDao.findAll(new Specification<MailAddresses>() {
            @Override
            public Predicate toPredicate(Root<MailAddresses> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                // 根据收件人筛选
                Predicate c1 = cb.equal(root.get("actor").as(Actor.class), addresses);
                // 不是用作回复的邮件
                Predicate c2 = cb.isNull(root.get("mail").get("parentMail").as(Mail.class));
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("获取用户收到的通知")
    public List<Mail> getListByAddress(Actor addresse) {
        List<Mail> result = mailDao.findAll(new Specification<Mail>() {

            @Override
            public Predicate toPredicate(Root<Mail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                // 根据收件人筛选
                Predicate c1 = cb.equal(root.get("addresses").as(Actor.class), addresse);
                // 不是用作回复的邮件
                Predicate c2 = cb.isNull(root.get("parentMail").as(Mail.class));
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        });
        return result;
    }

    @MethodDescription("设置已读")
    public void setHasRead(Mail mail, Actor addresse) {
        MailAddresses mailAddresses = mailAddressesDao.findOne(new Specification<MailAddresses>() {
            @Override
            public Predicate toPredicate(Root<MailAddresses> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                // 根据邮件和收件人获取唯一的一个对象
                Predicate c1 = cb.equal(root.get("mail").as(Mail.class), mail);
                Predicate c2 = cb.equal(root.get("actor").as(Actor.class), addresse);
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        });
        if (mailAddresses == null)
            throw new MessageException("您没有收到该邮件，不可查看！");
        // 设为已读
        mailAddresses.setIsRead(true);
        // 保存到数据库
        this.saveOrUpdate(mailAddresses);
    }

    @MethodDescription("获取当前用户未读的邮件数量")
    public Integer getNotReadMailNumber(Actor addresse) {
        Long result = mailAddressesDao.count(new Specification<MailAddresses>() {
            @Override
            public Predicate toPredicate(Root<MailAddresses> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("actor").as(Actor.class), addresse);
                Predicate c2 = cb.isNull(root.get("isRead").as(Boolean.class));
                // 没有阅读的邮件是父邮件
                Predicate c3 = cb.isNull(root.get("mail").get("parentMail").as(Mail.class));
                query.where(cb.and(c1, c2, c3));
                return query.getRestriction();
            }
        });
        // 返回查询总条数
        return Integer.valueOf(result.toString());

    }
}
