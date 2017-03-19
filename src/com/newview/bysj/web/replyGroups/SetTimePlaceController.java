package com.newview.bysj.web.replyGroups;

import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.domain.ReplyTime;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by zhan on 2016/3/17.
 */
@Controller
@RequestMapping("replyGroups")
public class SetTimePlaceController extends BaseController {

    private static final Logger logger = Logger.getLogger(SetTimePlaceController.class);

    /**
     * 答辩时间地点
     *
     * @param httpSession 当前会话，用于获取当前用户
     * @param modelMap    用于存储需要在jsp中获取的数据
     * @param pageNo      当前页
     * @param pageSize    每页显示的条数
     * @param title       课题
     * @return jsp
     */
    @RequestMapping(value = "/setReplyGroup.html", method = RequestMethod.GET)
    public String listReplyGroup(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, String title, HttpServletRequest httpServletRequest) {
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        Page<ReplyGroup> replyGroupPage = replyGroupService.getReplyGroupSchool(tutor.getDepartment().getSchool(), title, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, replyGroupPage, "replyGroupList", httpServletRequest.getRequestURI(), replyGroupPage.getTotalElements());
        modelMap.put("pageCount", replyGroupPage.getContent().size());
        if (title != null) {
            modelMap.put("title", title);
        }
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        return "replyGroup/setReplyGroupsTime";
    }


    /**
     * 答辩时间地点的get方法
     *
     * @param httpServletRequest 用于获取当前的请求路径
     * @param modelMap           存储需要在jsp中获取的数据
     * @param replyGroupId       需要修改的答辩小组的id
     * @return jsp
     */
    @RequestMapping(value = "/setTimeAndClassRoom.html", method = RequestMethod.GET)
    public String setTimeAndClassRoom(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer replyGroupId) {
        //获取需要修改的答辩小组
        ReplyGroup replyGroup = replyGroupService.findById(replyGroupId);
        modelMap.put("replyGroup", replyGroup);
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        modelMap.put("classList", classRoomService.findAll());
        modelMap.put("replyGroupId", replyGroupId);
        return "replyGroup/setTimeAndClassRoom";
    }

    /**
     * 答辩时间地点的post方法
     *
     * @param classRoomId  修改后的教室的id
     * @param replyGroupId 修改的答辩小组的id
     * @param startTime    开始时间
     * @param endTime      结束时间
     */
    @RequestMapping(value = "/setTimeAndClassRoom.html", method = RequestMethod.POST)
    public String setTimeAndClassRoom(Integer classRoomId, Integer replyGroupId, String startTime, String endTime) {
        //获取修改的答辩小组的名称
        ReplyGroup replyGroup = replyGroupService.findById(replyGroupId);
        //设置时间地点
        replyGroup.setReplyTime(new ReplyTime(CommonHelper.getCalendarByString(startTime), CommonHelper.getCalendarByString(endTime)));
        //设置答辩的都是
        replyGroup.setClassRoom(classRoomService.findById(classRoomId));
        replyGroupService.saveOrUpdate(replyGroup);
        return "redirect:/replyGroups/setReplyGroup.html";
    }
}
