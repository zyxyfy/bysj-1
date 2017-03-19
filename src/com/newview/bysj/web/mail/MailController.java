package com.newview.bysj.web.mail;

import com.newview.bysj.domain.*;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.other.PageCondition;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("notice")
public class MailController extends BaseController {

    private static final Logger logger = Logger.getLogger(MailController.class);

    /**
     * 获取发给我的通知
     *
     * @param httpSession   当前的会话
     * @param modelMap      将数据添加到modelMap中，便于在jsp中获取
     * @param pageCondition 存储页面的的相关信息
     * @return 跳转一个jsp页面
     */
    @RequestMapping("/noticesToMe.html")
    public String recevieMails(HttpSession httpSession, ModelMap modelMap, PageCondition pageCondition) {
        Actor actor = CommonHelper.getCurrentActor(httpSession);
        Page<MailAddresses> currentPage = mailAddressesService.getPageByAddresse(actor, pageCondition);
        CommonHelper.paging(modelMap, currentPage, "recevieMailList");
        modelMap.put("mailNum", currentPage.getContent().size());
        // 获取未读消息的条数
        modelMap.put("notreadmailnum", mailAddressesService.getNotReadMailNumber(actor));
        return "mail/recevieMail";
    }


    /**
     * 根据当前用户获取我发送的邮件
     *
     * @param httpSession   当前的会话
     * @param modelMap      传入到modelMap,以便在jsp中获取
     * @param pageCondition 有关分页的相关信息
     * @return 跳转到一个jsp
     */
    @RequestMapping("/myNotice.html")
    public String getMySendMails(HttpSession httpSession, ModelMap modelMap, PageCondition pageCondition, HttpServletRequest httpServletRequest) {
        Actor actor = CommonHelper.getCurrentActor(httpSession);
        Page<Mail> sendMails = mailService.getPageByAddresse(actor, pageCondition);
        modelMap.put("mailNum", sendMails.getNumberOfElements());
        //CommonHelper.paging(modelMap, sendMails, "sendMailList");
        CommonHelper.pagingHelp(modelMap, sendMails, "sendMailList", httpServletRequest.getRequestURI(), sendMails.getTotalElements());
        return "mail/sendMail";
    }


    /**
     * 发送邮件
     *
     * @param httpSession 当前会话
     * @param modelMap    数据添加到modelmap，从jsp中获取
     * @return jsp中填写邮件的页面  mail/addMail.jsp
     */
    @RequestMapping(value = "/sendMail.html", method = RequestMethod.GET)
    public String sendMail(HttpSession httpSession, ModelMap modelMap) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        /*if (CommonHelper.getCurrentActor(httpSession) instanceof Tutor) {
            tutor = (Tutor) CommonHelper.getCurrentActor(httpSession);
            tutor = tutorService.findById(tutor.getId());
        } else {
            throw new MessageException("你没有发送邮件的权限");
        }*/
        //获取我的学生
        List<Student> studentList = tutor.getStudent();
        Mail mail = new Mail();
        modelMap.put("mail", mail);
        modelMap.put("schoolList", schoolService.findAll());
        modelMap.put("myStudent", studentList);
        return "mail/addMail";
    }


    /**
     * 发送邮件
     *
     * @param sendMail            需要发送的邮件
     * @param addresseeIdStr      接收者的id字符串表示
     * @param httpSession         当前会话
     * @param httpServletResponse 根据当前请求生成的response对象
     * @param mailAttachment      发送的附件
     */
    @RequestMapping(value = "/sendMail.html", method = RequestMethod.POST)
    public String sendMail(Mail sendMail, @RequestParam("addressee.id") String addresseeIdStr, HttpSession httpSession, HttpServletResponse httpServletResponse, MultipartFile mailAttachment) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //根据id来获取用户，并使用set集合来去重
        Set<Actor> addressees = new HashSet<>();
        for (String addresseeId : addresseeIdStr.split(",")) {
            addressees.add(actorService.findById(Integer.valueOf(addresseeId)));
        }
        List<Actor> addresseeList = new ArrayList<>();
        Iterator<Actor> it = addressees.iterator();
        while (it.hasNext()) {
            addresseeList.add(it.next());
        }
        //设置收件人
        sendMail.setAddresses(addresseeList);
        //设置发布者
        sendMail.setAddressor(CommonHelper.getCurrentActor(httpSession));
        //设置发布时间
        sendMail.setAddressTime(CommonHelper.getNow());
        mailService.saveOrUpdate(sendMail);
        //如果有附件，则保存附件
        if (mailAttachment.getSize() > 0) {
            Attachment attachment = new Attachment();
            //获取上传文件的路径
            String fileName = mailAttachment.getOriginalFilename();
            String url = CommonHelper.fileUpload(mailAttachment, httpSession, "mail", fileName);
            attachment.setUrl(url);
            attachment.setIssuedDate(CommonHelper.getNow());
            attachment.setFileName(mailAttachment.getOriginalFilename());
            //保存附件
            attachmentService.saveOrUpdate(attachment);
            Attachment attachment1 = attachmentService.uniqueResult("url", url);
            if (attachment1 != null) {
                //与邮件进行关联
                sendMail.setAttachment(attachment);
            }
            mailService.saveOrUpdate(sendMail);
        }
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:myNotice.html";
    }


    /**
     * 删除邮件
     *
     * @param mailId              要删除的邮件的id
     * @param httpSession         当前的会话
     * @param httpServletResponse 根据当前请求生成response对象
     */
    @RequestMapping("/delMail.html")
    public void delMailById(Integer mailId, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        //得到要删除的mail
        Mail mailToDel = mailService.findById(mailId);
        Actor actor = CommonHelper.getCurrentActor(httpSession);
        //判断当前用户是否有权限删除邮件
        if (!mailToDel.getAddressor().getId().equals(actor.getId())) {
            //throw new MessageException("你不是邮件的发布者，没有权限删除该邮件");
            CommonHelper.buildSimpleJson(httpServletResponse, "你不是邮件的发布者，没有权限删除该邮件");
        }
        //如果有附件，则删除和此邮件有关的附件
        if (mailToDel.getAttachment() != null) {
            CommonHelper.delete(httpSession, mailToDel.getAttachment().getUrl());
        }
        //mailService.deleteById(mailId);
        //mailService.deleteObject(mailToDel);
        //使用以上两种方法不能成功的删除邮件
        mailService.deleteMail(mailId, httpSession);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }


    @RequestMapping(value = "/editMail.html", method = RequestMethod.GET)
    public String editMail(HttpSession httpSession, ModelMap modelMap, Integer mailIdToEdit, HttpServletRequest httpServletRequest) {
        //根据id获取当前要修改的id
        Mail mailToEdit = mailService.findById(mailIdToEdit);
        //获取当前请求的url
        String actionUrl = CommonHelper.getRequestUrl(httpServletRequest);
        modelMap.put("actionUrl", actionUrl);
        modelMap.put("mail", mailToEdit);
        modelMap.put("mailIdToEdit", mailToEdit.getId());
        return "mail/editMail";
    }

    @RequestMapping(value = "/editMail.html", method = RequestMethod.POST)
    public String editMail(HttpServletResponse httpServletResponse, Mail mail, HttpSession httpSession, Integer mailIdToEdit, MultipartFile mailAttachment) {
        //logger.error("title-------"+mail.getTitle());
        //从数据库获取需要修改的邮件
        Mail mailToEdit = mailService.findById(mailIdToEdit);
        Actor actor = CommonHelper.getCurrentActor(httpSession);
        if (mailToEdit.getAddressor().equals(actor))
            throw new MessageException("你不是邮件的发布者，没有权限修改邮件");
        mailToEdit.setTitle(mail.getTitle());
        mailToEdit.setContent(mail.getContent());
        mailToEdit.setAddressTime(CommonHelper.getNow());
        //判断有没有上传附件
        if (mailAttachment.getSize() > 0) {
            //如果之前没有上传附件，则创建一个新的附件。如果之前已经上传附件，则删除原来的附件，重新上传。
            if (mailToEdit.getAttachment() == null) {
                Attachment attachment = new Attachment();
                String url = CommonHelper.fileUpload(mailAttachment, httpSession, "mail", mailAttachment.getOriginalFilename());
                attachment.setUrl(url);
                attachment.setFileName(mailAttachment.getOriginalFilename());
                attachment.setIssuedDate(CommonHelper.getNow());
                attachmentService.save(attachment);
                //mail和attachment进行关联
                mailToEdit.setAttachment(attachment);
            } else {
                Attachment attachment = mailToEdit.getAttachment();
                CommonHelper.delete(httpSession, attachment.getUrl());
                String url = CommonHelper.fileUpload(mailAttachment, httpSession, "mail", attachment.getId());
                attachment.setUrl(url);
                attachmentService.update(attachment);
            }
        }
        mailService.saveOrUpdate(mailToEdit);
//        CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:myNotice.html";
    }


    //文件下载
    @RequestMapping("/downloadMail.html")
    public ResponseEntity<byte[]> downloadMailAttachment(Integer mailId, HttpSession httpSession) {
        Mail mail = mailService.findById(mailId);
        if (mail.getAttachment() != null) {
            String downloadUrl = mail.getAttachment().getUrl();
            try {
                return CommonHelper.download(httpSession, downloadUrl, mail.getAttachment().getFileName());
            } catch (IOException e) {
                throw new MessageException("下载文件出错，请稍后再试");
            }
        }
        return null;
    }


    //查看邮件，并列出与此邮件有关所有的回复邮件
    @RequestMapping("/receiveViewMail.html")
    public String viewMail(HttpSession httpSession, ModelMap modelMap, Integer mailId, PageCondition pageCondition) {
        //获取要查看的mail
        Mail viewMail = mailService.findById(mailId);
        //如果接收者查看消息，则设置消息为已读
        List<Actor> actorList = viewMail.getAddresses();
        List<String> nameList = new ArrayList<>();
        for (Actor actorStr : actorList) {
            nameList.add(actorStr.getName());
        }
        //logger.error("-=-=-=-=" + nameList.contains(CommonHelper.getCurrentActor(httpSession).getName()));
        if (nameList.contains(CommonHelper.getCurrentActor(httpSession).getName())) {
            //logger.error("currentAddressor" + viewMail.getAddressor());
            mailAddressesService.setHasRead(viewMail, CommonHelper.getCurrentActor(httpSession));
        }

        if (viewMail.getReplyMail() != null) {
            Page<Mail> replyMailByPage = mailService.getReplyMail(viewMail, pageCondition);
            CommonHelper.paging(modelMap, replyMailByPage, "replyMailList");
        }
        modelMap.put("mail", viewMail);
        return "mail/receiveViewMail";
    }


    @RequestMapping("/sendViewMail.html")
    public String viewSendMail(HttpSession httpSession, ModelMap modelMap, Integer mailId, PageCondition pageCondition) {
        //获取要查看的mail
        Mail viewMail = mailService.findById(mailId);
        //如果接收者查看消息，则设置消息为已读
        List<Actor> actorList = viewMail.getAddresses();
        List<String> nameList = new ArrayList<>();
        for (Actor actorStr : actorList) {
            nameList.add(actorStr.getName());
        }
        //logger.error("-=-=-=-=" + nameList.contains(CommonHelper.getCurrentActor(httpSession).getName()));
        if (nameList.contains(CommonHelper.getCurrentActor(httpSession).getName())) {
            //logger.error("currentAddressor" + viewMail.getAddressor());
            mailAddressesService.setHasRead(viewMail, CommonHelper.getCurrentActor(httpSession));
        }

        if (viewMail.getReplyMail() != null) {
            Page<Mail> replyMailByPage = mailService.getReplyMail(viewMail, pageCondition);
            CommonHelper.paging(modelMap, replyMailByPage, "replyMailList");
        }
        modelMap.put("mail", viewMail);
        return "mail/sendViewMail";
    }


    @RequestMapping(value = "/replyMail.html", method = RequestMethod.GET)
    public String replyMail(ModelMap modelMap, Integer parentMailId) {
        Mail parentMail = mailService.findById(parentMailId);
        modelMap.put("mail", parentMail);
        return "mail/replyMail";
    }

    //回复邮件
    @RequestMapping(value = "/replyMail.html", method = RequestMethod.POST)
    public void replyMail(HttpSession httpSession, Integer parentMailId, String content, HttpServletResponse httpServletResponse) {
        //得到父邮件
        Mail parentMail = mailService.findById(parentMailId);
        List<Actor> actorList = new ArrayList<>();
        actorList.add(parentMail.getAddressor());
        //创建回复邮件
        Mail replyMail = new Mail();
        String title = "回复(" + parentMail.getTitle() + ")的邮件";
        replyMail.setAddressTime(CommonHelper.getNow());
        replyMail.setTitle(title);
        replyMail.setContent(content);
        replyMail.setAddressor(CommonHelper.getCurrentActor(httpSession));
        //设置接收者：父邮件的发送者
        replyMail.setAddresses(actorList);
        mailService.save(replyMail);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

}
