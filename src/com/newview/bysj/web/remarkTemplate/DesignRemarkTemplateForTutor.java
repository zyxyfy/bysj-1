package com.newview.bysj.web.remarkTemplate;

import com.newview.bysj.domain.RemarkTemplateForDesignTutor;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 指导老师设计课题的评语模版
 * Created 2016/3/27,22:00.
 * Author 张战.
 */

@Controller
@RequestMapping("evaluate")
public class DesignRemarkTemplateForTutor extends BaseRemarkTemplate {

//    private static final Logger logger = Logger.getLogger(DesignRemarkTemplateForTutor.class);

    /**
     * 设置评语模版的get方法
     *
     * @param modelMap           将数据添加到map集合中，用于在jsp中获取
     * @param httpServletRequest 当前请求，用于获取请求的url
     * @return jsp
     */
    @RequestMapping(value = "/setDesignRemarkForTutor.html", method = RequestMethod.GET)
    public String setRemarkTemplate(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        modelMap.put("remarkTemplate", new RemarkTemplateForDesignTutor());
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        return folderName + "saveRemarkTemplate";
    }

    /**
     * 设置评语模版的post方法
     *
     * @param httpSession         当前会话
     * @param title               评语模版的标题
     * @param lineNumber          评语选项的行数
     * @param httpServletResponse 对客户端的响应，这个参数在方法体中没有用到
     * @param httpServletRequest  当前请求
     * @return jsp
     */
    @RequestMapping(value = "/setDesignRemarkForTutor.html", method = RequestMethod.POST)
    public String setRemarkTemplate(HttpSession httpSession, String title, Integer lineNumber, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
//        logger.debug("title" + title);
//        logger.debug("lineNumber " + lineNumber);
        //创建一个评语模版对象
        RemarkTemplateForDesignTutor remarkTemplateForDesignTutor = new RemarkTemplateForDesignTutor();
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //设置所属的教研室
        remarkTemplateForDesignTutor.setDepartment(tutor.getDepartment());
        //保存到数据库
        return super.saveRemarkTemplate(httpServletResponse, httpSession, httpServletRequest, title, lineNumber, remarkTemplateForDesignTutor);
    }
}
