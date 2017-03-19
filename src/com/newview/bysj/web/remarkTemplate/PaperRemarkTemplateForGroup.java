package com.newview.bysj.web.remarkTemplate;

import com.newview.bysj.domain.RemarkTemplateForDesignReviewer;
import com.newview.bysj.domain.RemarkTemplateForPaperGroup;
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
 * 答辩组长论文课题的评语模版
 * Created 2016/3/27,22:57.
 * Author 张战.
 */

@Controller
@RequestMapping("evaluate")
public class PaperRemarkTemplateForGroup extends BaseRemarkTemplate {


    /**
     * 设置评语模版的get方法
     *
     * @param modelMap 将数据添加到map集合中，用于在jsp中获取
     * @param request  当前请求，用于获取请求的url
     * @return 设置评语模版的界面
     */
    @RequestMapping(value = "/setPaperRemarkTemplateForGroup.html", method = RequestMethod.GET)
    public String setRemarkTemplateForPaperGroup(ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("actionUrl", request.getRequestURI());
        modelMap.put("remarkTemplate", new RemarkTemplateForDesignReviewer());
        return folderName + "saveRemarkTemplate";
    }


    /**
     * 设置评语模版的post方法
     *
     * @param request             当前请求
     * @param lineNumber          评语选项的条数
     * @param title               评语的标题
     * @param httpSession         当前会话
     * @param httpServletResponse 对客户端的响应，这个参数在方法体中没有用到
     * @return jsp
     */
    @RequestMapping(value = "/setPaperRemarkTemplateForGroup.html", method = RequestMethod.POST)
    public String setRemarkTemplateForPaperGroup(HttpServletRequest request, Integer lineNumber, String title, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        //新建评语模板
        RemarkTemplateForPaperGroup remarkTemplateForPaperGroup = new RemarkTemplateForPaperGroup();
        Tutor tutor = (Tutor) CommonHelper.getCurrentActor(httpSession);
        //设置所属的教研室
        remarkTemplateForPaperGroup.setDepartment(tutor.getDepartment());
        //保存到数据库
        return super.saveRemarkTemplate(httpServletResponse, httpSession, request, title, lineNumber, remarkTemplateForPaperGroup);
    }
}
