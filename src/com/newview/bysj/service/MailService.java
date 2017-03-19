package com.newview.bysj.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.MailDao;
import com.newview.bysj.domain.Actor;
import com.newview.bysj.domain.Attachment;
import com.newview.bysj.domain.Mail;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import com.newview.bysj.other.PageCondition;

@Service("mailService")
public class MailService extends BasicService<Mail, Integer> {

    MailDao mailDao;
    @Autowired
    AttachmentService attachmentService;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Mail, Integer> basicDao) {
        this.basicDao = basicDao;
        mailDao = (MailDao) basicDao;
    }

    @MethodDescription("获取我发布的邮件")
    public Page<Mail> getPageByAddresse(Actor actor, PageCondition pageCondition) {
        Integer pageNo = CommonHelper.getPageNo(pageCondition.getPageNow(), pageCondition.getPageSize());
        Integer pageSize = CommonHelper.getPageSize(pageCondition.getPageSize());
        Page<Mail> result = mailDao.findAll(new Specification<Mail>() {

            @Override
            public Predicate toPredicate(Root<Mail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //筛选发布者
                Predicate c1 = cb.equal(root.get("addressor").as(Actor.class), actor);
                //获取主mail
                Predicate c2 = cb.isNull(root.get("parentMail").as(Mail.class));
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("获取我发布的邮件")
    public List<Mail> getListByAddresse(Actor actor) {
        List<Mail> result = mailDao.findAll(new Specification<Mail>() {
            @Override
            public Predicate toPredicate(Root<Mail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //筛选发布者
                Predicate c1 = cb.equal(root.get("addressor").as(Actor.class), actor);
                //获取主mail
                Predicate c2 = cb.isNull(root.get("parentMail").as(Mail.class));
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        });
        return result;
    }

    @MethodDescription("获取回复的邮件")
    public Page<Mail> getReplyMail(Mail mailToView, PageCondition pageCondition) {
        Integer pageNo = CommonHelper.getPageNo(pageCondition.getPageNow(), pageCondition.getPageSize());
        Integer pageSize = CommonHelper.getPageSize(pageCondition.getPageSize());
        Page<Mail> result = mailDao.findAll(new Specification<Mail>() {
            @Override
            public Predicate toPredicate(Root<Mail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("parentMail").as(Mail.class), mailToView);
                query.where(condition);
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize));
        return result;

    }

    @MethodDescription("删除邮件")
    public void deleteMail(Integer mailId, HttpSession httpSession) {
        //获取要删除的邮件
        Mail mailToDelete = mailDao.findOne(mailId);
        //删除收件人信息
        mailToDelete.setAddresses(null);
        //删除回复的邮件
        for (Mail mail : mailToDelete.getReplyMail()) {
            mail.setParentMail(null);
            mailDao.merge(mail);
            //递归删除，防止回复信息过多不能删除，直到没有回复信息为止
            this.deleteById(mail.getId());
        }
        this.update(mailToDelete);
        //删除附件
        if (mailToDelete.getAttachment() != null) {
            Attachment attachment = mailToDelete.getAttachment();
            //删除文件
            CommonHelper.delete(httpSession, attachment.getUrl());
            attachmentService.deleteObject(attachment);
        }
        //delelte
        this.deleteObject(mailToDelete);
    }


}
