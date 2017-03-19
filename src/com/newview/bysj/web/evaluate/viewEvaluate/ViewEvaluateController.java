package com.newview.bysj.web.evaluate.viewEvaluate;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zhan on 2016/4/8.
 */
@Controller
public class ViewEvaluateController extends BaseController {

    private static final Logger logger = Logger.getLogger(ViewEvaluateController.class);

    /**
     * 查看指导老师评审表
     *
     * @param projectId 要查看的评审表对应课题的id
     * @param modelMap  存储需要在jsp中获取的数据
     * @return jsp
     */
    @RequestMapping(value = "/viewEvaluate.html", method = RequestMethod.GET)
    public String viewTutorEvaluate(Integer projectId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        return "evaluate/viewEvaluate/viewTutorEvaluate";
    }

    @RequestMapping("/viewReviewerEvaluate.html")
    public String viewReviewerEvaluate(Integer projectId, ModelMap modelMap) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        return "evaluate/viewEvaluate/viewReviewerEvaluate";
    }

    @RequestMapping(value = "/viewGroupEvaluate.html")
    public String viewEvaluateByGroup(ModelMap modelMap, Integer projectId) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        //logger.error("-----1231231__-------");
        return "evaluate/viewEvaluate/viewReplyGroupEvaluate";
    }


    //打印论文附件查看答辩小组意见表
    @RequestMapping(value = "/viewGroupEva.html")
    public String viewEvaByGroup(ModelMap modelMap, Integer projectId, Integer pageNo, Integer pageSize) {
        GraduateProject graduateProject = graduateProjectService.findById(projectId);
        modelMap.put("graduateProject", graduateProject);
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        //logger.error("-----1231231__-------");
        return "evaluate/viewEvaluate/viewReplyGroupEva";
    }

    @RequestMapping("/downloadFinalDraft.html")
    public ResponseEntity<byte[]> downloadFinal(Integer projectIdToEvaluate, HttpSession httpSession) {
        GraduateProject graduateProject = graduateProjectService.findById(projectIdToEvaluate);
        try {
            return CommonHelper.download(httpSession, graduateProject.getFinalDraft(), "论文终稿");
        } catch (IOException e) {
            throw new MessageException("下载失败");
        }
    }
}

