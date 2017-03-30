package com.newview.bysj.web.replyGroups;

import com.newview.bysj.domain.*;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created 2016/3/1,14:28.
 * Author 张战.
 */
@Controller
@RequestMapping("process")
public class ReplyGroupsController extends BaseController {
    //private static Integer num =0;

    private static final Logger logger = Logger.getLogger(ReplyGroupsController.class);

    /**
     * 答辩分组安排
     *
     * @param httpSession        当前会话，用于获取当前的用户
     * @param modelMap           map集合，用于存储需要在jsp中获取的数据
     * @param pageNo             当前页
     * @param pageSize           每页的条数
     * @param httpServletRequest 用于获取请求的路径
     * @return jsp
     */
    @RequestMapping(value = "/saveReplyGroups.html", method = RequestMethod.GET)
    public String replyGroups(HttpSession httpSession, ModelMap modelMap, String replyGroupName, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //根据名称和教研室获取答辩小组
        Page<ReplyGroup> replyGroupPage = replyGroupService.getReplyGroupByDepartmentAndName(tutor.getDepartment(), replyGroupName, pageNo, pageSize);
        //CommonHelper.paging(modelMap, replyGroupPage, "replyGroupList");
        CommonHelper.pagingHelp(modelMap, replyGroupPage, "replyGroupList", httpServletRequest.getRequestURI(), replyGroupPage.getTotalElements());
        //在搜索框中显示之前搜索的小组的名字
        /*if (logger.isDebugEnabled())
            logger.debug("grouName: " + replyGroupName);
        modelMap.put("groupName", replyGroupName);*/
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        return "replyGroup/replyGroups";
    }

    /**
     * 根据id删除答辩小组
     *
     * @param replyGroupId        需要删除的答辩小组的id
     * @param httpServletResponse 用于给浏览器返回json数据
     */
    @RequestMapping(value = "/delReplyGroupById", method = RequestMethod.POST)
    public void delReplyGroupById(Integer replyGroupId, HttpServletResponse httpServletResponse) {
        ReplyGroup replyGroup = replyGroupService.findById(replyGroupId);
        if (replyGroup == null) {
            CommonHelper.buildSimpleJson(httpServletResponse, "该小组不存在，可能已经删除");
            return;
        }
        //取消graduateProject中关联的小组
        List<GraduateProject> graduateProjectList = replyGroup.getGraduateProject();
        for (GraduateProject graduateProject : graduateProjectList) {
            graduateProject.setReplyGroup(null);
            graduateProjectService.saveOrUpdate(graduateProject);
        }
        //删除答辩小组中的整条记录，不需要再对每列数据取消关联
        /*replyGroup.setDepartment(null);
        replyGroup.setClassRoom(null);
        replyGroup.setGraduateProject(null);
        replyGroup.setStudent(null);*/
        //取消与学生的关联
        List<Student> students = replyGroup.getStudent();
        for (Student student : students) {
            student.setReplyGroup(null);
            studentService.saveOrUpdate(student);
        }
        replyGroupService.delete(replyGroupId);
        CommonHelper.buildSimpleJson(httpServletResponse, "删除成功！");
    }

    /**
     * 添加答辩小组的get方法
     *
     * @param httpSession        当前会话，用于获取当前的用户
     * @param httpServletRequest 用于获取请求的url
     * @param modelMap           map集合，用于存储需要在jsp获取的数据
     * @return jsp
     */
    @RequestMapping(value = "/addReplyGroup", method = RequestMethod.GET)
    public String addReplyGroup(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap) {
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        modelMap.put("replyGroup", new ReplyGroup());
        modelMap.put("tutors", tutor.getDepartment().getTutor());
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        return "replyGroup/addOrEditReplyGroup";
    }

    /**
     * 添加答辩小组的post方法
     *
     * @param httpSession         当前会话，用于获取当前的用户
     * @param leaderId            答辩小组组长的id
     * @param studentIds          答辩小组学生的ids
     * @param tutorIds            答辩老师的ids
     * @param replyGroupName      答辩小组的名称，用于通过名字对答辩小组进行检索
     * @param httpServletResponse 用于给浏览器返回json数据
     */
    @RequestMapping(value = "/addReplyGroup", method = RequestMethod.POST)
    public String addReplyGroup(HttpSession httpSession, Integer leaderId, Integer[] studentIds, Integer[] tutorIds, String replyGroupName, HttpServletResponse httpServletResponse) {
        // TODO: 2017/3/26 此处不当！
        Integer num = replyGroupService.getNum() + 1;
        //获取当前的用户
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        //获取指定的答辩小组组长
        Tutor leader = tutorService.findById(leaderId);
        //实例化一个小组
        ReplyGroup replyGroup = new ReplyGroup();
        replyGroup.setDepartment(tutor.getDepartment());
        //设置答辩小组的名称
        replyGroup.setDescription(replyGroupName);
        replyGroup.setLeader(leader);
        //用于获取保存后的结果
        replyGroup.setNum(num);
        //设置答辩小组的答辩老师名单
        replyGroup.setMembers(this.getTutorById(tutorIds));
        replyGroupService.save(replyGroup);
        logger.info("======"+replyGroup.getId());
        //重新获取保存后的replyGroup否则更新graduateProject会出错
        replyGroup = replyGroupService.uniqueResult("num", num);
        Role role = roleService.uniqueResult("roleName", "ROLE_REPLYTEAMHEADMAN");
        //判断指定的答辩小组组长是否有答辩小组组长的角色，如果没有则设置角色
        userRoleService.insertRole(leader.getUser(), role);
        //用于存放答辩小组中所有学生的课题，通过set集合来去重
        List<GraduateProject> graduateProjectSet = new ArrayList<>();
        //List<GraduateProject> graduateProjectList = new ArrayList<>();
        GraduateProject graduateProject;
        for (Integer studentId : studentIds) {
            Student student = studentService.findById(studentId);
            if (student.getGraduateProject() == null) {
                throw new MessageException("没有选择课题，不能参加答辩！");
            }
            if (student.getGraduateProject().getReplyGroup() != null) {
                throw new MessageException("已选择答辩小组，不能重新选择");
            }

            graduateProject = student.getGraduateProject();
            graduateProject.setReplyGroup(replyGroup);
            graduateProjectService.update(graduateProject);
            //对更新状态进行保存
            graduateProjectService.save(graduateProject);
            graduateProjectSet.add(graduateProject);
        }
        //设置答辩小组的学生
        for (Integer studentId : studentIds) {
            Student student = studentService.findById(studentId);
            student.setReplyGroup(replyGroup);
            studentService.saveOrUpdate(student);
        }
        //给答辩小组关联课题
        replyGroup.setGraduateProject(graduateProjectSet);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/process/saveReplyGroups.html";
    }


    public List<Tutor> getTutorById(Integer[] tutorIds) {
        List<Tutor> tutorList = new ArrayList<>();
        Tutor tutor;
        for (Integer tutorId : tutorIds) {
            tutor = tutorService.findById(tutorId);
            tutorList.add(tutor);
        }
        return tutorList;
    }

    /**
     * 修改答辩小组的get方法，与添加答辩小组用的是同一个jsp
     *
     * @param httpSession        用于获取当前用户
     * @param modelMap           用于存储需要在jsp获取的数据
     * @param groupId            需要修改的答辩小组的id
     * @param httpServletRequest 当前请求，用于获取请求的路径
     * @return jsp
     */
    @RequestMapping(value = "/editReplyGroup", method = RequestMethod.GET)
    public String editReplyGroup(HttpSession httpSession, ModelMap modelMap, Integer groupId, HttpServletRequest httpServletRequest) {
        ReplyGroup replyGroup = replyGroupService.findById(groupId);
        Tutor tutor = tutorService.findById(CommonHelper.getCurrentTutor(httpSession).getId());
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        modelMap.put("replyGroup", replyGroup);
        modelMap.put("tutors", tutor.getDepartment().getTutor());
        List<Student> students = new ArrayList<>();
        for (GraduateProject graduateProject : replyGroup.getGraduateProject()) {
            students.add(graduateProject.getStudent());
        }
        modelMap.put("students", students);
        return "replyGroup/addOrEditReplyGroup";
    }


    /**
     * 修改答辩小组的post方法
     *
     * @param leaderId            修改后的答辩小组组长的id
     * @param studentIds          修改后的学生的ids
     * @param tutorIds            修改后的老师的ids
     * @param replyGroupName      答辩小组的名字，用于通过名字进行检索
     * @param httpServletResponse 用于给浏览器输出json数据
     */
    @RequestMapping(value = "/editReplyGroup", method = RequestMethod.POST)
    public String editReplyGroup(Integer replyGroupId, Integer leaderId, Integer[] cancelStuIds, Integer[] studentIds, Integer[] tutorIds, String replyGroupName, HttpServletResponse httpServletResponse) {
        //获取需要修改的答辩小组
        ReplyGroup replyGroup = replyGroupService.findById(replyGroupId);
        //存放修改后的学生
        List<Student> students = new ArrayList<>();
        for (GraduateProject graduateProject : replyGroup.getGraduateProject()) {
            students.add(graduateProject.getStudent());
        }

        //存放需要删除的学生
        List<Student> cancelStu = new ArrayList<>();
        if (cancelStuIds != null) {
            for (Integer cancelStuId : cancelStuIds) {
                cancelStu.add(studentService.findById(cancelStuId));
            }

        }
        students.removeAll(cancelStu);
        if (studentIds != null) {
            for (Integer studentId : studentIds) {
                students.add(studentService.findById(studentId));
            }
        }
        //获取原来的课题
        List<GraduateProject> originalGraduateProject = replyGroup.getGraduateProject();
        //取消所有的关联
        for (GraduateProject project : originalGraduateProject) {
            project.setReplyGroup(null);
            graduateProjectService.saveOrUpdate(project);
        }
        //修改答辩小组的名称
        replyGroup.setDescription(replyGroupName);
        //修改答辩小组的组长
        replyGroup.setLeader(tutorService.findById(leaderId));
        replyGroup.setStudent(students);
        //修改答辩小组的成员
        replyGroup.setMembers(this.getTutorById(tutorIds));
        replyGroup.setGraduateProject(null);
        replyGroupService.update(replyGroup);
        replyGroupService.save(replyGroup);

        //新修改后的课题
        List<GraduateProject> graduateProjectList = new ArrayList<>();
        GraduateProject graduateProject;
        for (Student student : students) {
            graduateProject = student.getGraduateProject();
            if (graduateProject == null) {
                throw new MessageException("没有选择课题，不能参加答辩");
            }
            graduateProjectList.add(graduateProject);
        }
        replyGroup.setGraduateProject(graduateProjectList);
        replyGroupService.update(replyGroup);
        replyGroupService.save(replyGroup);

        //重新对课题进行关联
        for (GraduateProject project : graduateProjectList) {
            project.setReplyGroup(replyGroup);
            graduateProjectService.saveOrUpdate(project);
        }
        return "redirect:/process//saveReplyGroups.html";
    }

    /**
     * 删除集合A中与集合B相同的部分。如A：[1，2，3]    B：[3]    则返回 A：[1，2]
     *
     * @param graduateProjectA 需要移除的元素的集合
     * @param graduateProjectB 用于和集合A比较找出相同的部分
     * @return 移除与集合B存在相同元素后的新的A集合
     */
    public List<GraduateProject> getDifferent(Collection<GraduateProject> graduateProjectA, Collection<GraduateProject> graduateProjectB) {
        //需要对原来的集合进行复制，以免损坏原来的集合
        List<GraduateProject> a = clone(graduateProjectA);
        List<GraduateProject> b = clone(graduateProjectB);
        a.removeAll(b);
        return a;
    }

    /**
     * 对集合进行复制
     *
     * @param originalGraduateProject 需要复制的集合
     * @return 与原集合相同的集合
     */
    public List<GraduateProject> clone(Collection<GraduateProject> originalGraduateProject) {
        List<GraduateProject> cloneGraduateProject = originalGraduateProject.stream().collect(Collectors.toList());
        return cloneGraduateProject;
    }


    //安排答辩时间地点的方法
   /* @RequestMapping(value = "replyGroups/setReplyGroups.html", method = RequestMethod.GET)
    public String setReplyGroup() {

        return "process/setReplyGroupsTime";
    }*/
}
