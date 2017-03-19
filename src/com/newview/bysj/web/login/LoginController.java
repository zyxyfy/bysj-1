package com.newview.bysj.web.login;

import com.newview.bysj.domain.*;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.other.PageCondition;
import com.newview.bysj.other.ResourcesComparatorById;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class LoginController extends BaseController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping("/login.html")
    public String login() {
        return "login/login";
    }

    /**
     * 重新登录的controller
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/reLogin.html")
    public String reLogin(ModelMap modelMap) {
        modelMap.put("error", "请重新登录");
        return "login/login";
    }

    @RequestMapping("/logout.html")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "login/login";

    }

    @RequestMapping(value = "updateInfo.html", method = RequestMethod.GET)
    public String ownInfo(HttpSession httpSession, ModelMap modelMap, HttpServletRequest httpServletRequest) {
        Actor actor = CommonHelper.getCurrentActor(httpSession);
        Contact contact;
        if (actor.getContact() == null) {
            contact = new Contact();
        } else {
            contact = actor.getContact();
        }
        modelMap.clear();
        modelMap.put("actor", actor);
        modelMap.put("contact", contact);
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        return "login/updateInfo";
    }


    @RequestMapping(value = "/updateInfo.html", method = RequestMethod.POST)
    public String ownInfo(Contact contact, HttpSession httpSession, MultipartFile signatureFile, HttpServletResponse httpServletResponse) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        tutor.setContact(null);
        tutorService.update(tutor);
        tutor.setContact(contact);
        try {
            System.out.println(signatureFile.getOriginalFilename());
            if (signatureFile.getOriginalFilename() != null) {
                //上传签名照片
                tutor.setSignPictureURL(CommonHelper.fileUpload(signatureFile, httpSession, "signatureImg", tutor.getId()));
            }
        } catch (Exception e) {

        }
        tutorService.saveOrUpdate(tutor);
        return "redirect:index.html";
        //CommonHelper.buildSimpleJson(httpServletResponse);
    }


    @RequestMapping(value = "/viewSignatureImg.html")
    public ResponseEntity<byte[]> viewSignature(HttpSession httpSession) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        File file = new File(CommonHelper.getUploadPath(httpSession) + tutor.getSignPictureURL());
        if (!file.exists()) {
            logger.error("===============" + CommonHelper.getUploadPath(httpSession) + "/images/signature/open.png");
            file = new File("images/signature/open.png");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置默认的名字
        httpHeaders.setContentDispositionFormData("attachment", "checkImg.jpg");
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new MessageException("获取签名照失败");
        }
    }


    @RequestMapping(value = "/updatePassword.html", method = RequestMethod.POST)
    public void updatePassword(HttpSession httpSession, String oldPassword, String newPassword, String confirmNewPassword, HttpServletResponse httpServletResponse) {
        User user = CommonHelper.getCurrentUser(httpSession);
        /*if (!CommonHelper.makeMD5(oldPassword).equals(user.getPassword())) {
            CommonHelper.buildSimpleJson(httpServletResponse, "原始密码输入错误");
            return;
        }*/
        if (!CommonHelper.makeMD5(oldPassword).equals(user.getPassword())) {
            try {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.getWriter().print("原始密码输入错误");
            } catch (IOException e) {
                throw new MessageException("修改密码失败");
            }
        } else {
            user.setPassword(CommonHelper.makeMD5(newPassword));
            userService.saveOrUpdate(user);
            try {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.getWriter().print("修改成功");
            } catch (IOException e) {
                throw new MessageException("修改密码失败");
            }
        }
    }

    /**
     * 获取当前用户所拥有的资源
     *
     * @param httpSession
     * @param modelMap
     * @return
     */
    @RequestMapping("/index.html")
    public String index(HttpSession httpSession, ModelMap modelMap, PageCondition pageCondition) {
        Set<Resource> resource = new HashSet<Resource>();
        User user = userService.findById(CommonHelper.getCurrentUser(httpSession).getId());
        List<UserRole> userRoles = user.getUserRole();
        for (UserRole userRole : userRoles) {
            for (RoleResource roleResource : userRole.getRole().getRoleResource()) {
                resource.add(roleResource.getResource().getParent());
                //logger.debug("message parent resource" + roleResource.getResource().getParent());
            }
        }
        List<Resource> parentResourceList = new ArrayList<Resource>(resource);
        //调用工具类中的排序方法对集合中的元素进行排序
        Collections.sort(parentResourceList, new ResourcesComparatorById());
        //清空集合中的元素，用来盛放子资源。不需要再重新创建一个对象
        resource.clear();
        for (UserRole userRole : userRoles) {
            for (RoleResource roleResource : userRole.getRole().getRoleResource()) {
                resource.add(roleResource.getResource());
            }
        }
        List<Resource> childResourceList = new ArrayList<Resource>(resource);
        Collections.sort(childResourceList, new ResourcesComparatorById());
        Actor actor = CommonHelper.getCurrentActor(httpSession);
        Page<MailAddresses> currentPage = mailAddressesService.getPageByAddresse(actor, pageCondition);
        CommonHelper.paging(modelMap, currentPage, "recevieMailList");
        modelMap.put("mailNum", currentPage.getContent().size());
        // 获取未读消息的条数
        modelMap.put("notreadmailnum", mailAddressesService.getNotReadMailNumber(actor));
        //将要解析的数据添加到map中，在jsp中进行解析
        modelMap.put("parentResourceList", parentResourceList);
        modelMap.put("childResourceList", childResourceList);
        modelMap.put("user", user);
        return "login/index";
    }
}
