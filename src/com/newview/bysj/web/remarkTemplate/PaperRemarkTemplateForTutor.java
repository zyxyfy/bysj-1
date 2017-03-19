package com.newview.bysj.web.remarkTemplate;

import com.newview.bysj.domain.RemarkTemplateForPaperTutor;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 指导老师的论文课题评语模版
 * Created 2016/3/27,22:56.
 * Author 张战.
 */
@Controller
@RequestMapping("evaluate")
public class PaperRemarkTemplateForTutor extends BaseRemarkTemplate {

    /**
     * 设置评语模版的set方法
     *
     * @param modelMap           将数据添加到map集合中，用于在jsp中获取
     * @param httpServletRequest 当前请求，用于获取请求的url
     * @return 设置评语模版的界面
     */
    @RequestMapping(value = "/setPaperRemarkForTutor.html", method = RequestMethod.GET)
    public String setRemarkTemplate(ModelMap modelMap, HttpServletRequest httpServletRequest) {
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        modelMap.put("remarkTemplate", new RemarkTemplateForPaperTutor());
        return super.folderName + "saveRemarkTemplate";
    }

    /**
     * 设置评语模版的post方法
     *
     * @param title               评语的标题
     * @param httpServletResponse 对客户端的响应，这个参数在方法体中没有用到
     * @param lineNumber          评语选项的条数
     * @param httpSession         当前会话
     * @param httpServletRequest  当前请求
     * @return jsp
     */
    @RequestMapping(value = "/setPaperRemarkForTutor.html", method = RequestMethod.POST)
    public String setRemarTemplate(String title, HttpServletResponse httpServletResponse, Integer lineNumber, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        System.out.println("----------" + lineNumber);
        //创建一个评语模版对象
        RemarkTemplateForPaperTutor remarkTemplateForPaperTutor = new RemarkTemplateForPaperTutor();
        //获取当前tutor
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //设置所属教研室
        remarkTemplateForPaperTutor.setDepartment(tutor.getDepartment());
        //保存到数据库
        return super.saveRemarkTemplate(httpServletResponse, httpSession, httpServletRequest, title, lineNumber, remarkTemplateForPaperTutor);
    }


}
