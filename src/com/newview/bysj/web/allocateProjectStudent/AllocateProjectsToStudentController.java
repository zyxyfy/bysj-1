package com.newview.bysj.web.allocateProjectStudent;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.MainTutorage;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
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
import java.util.List;

/**
 * 给学生分配课题
 * Created 2016/3/1,13:24.
 * Author 张战.
 */
@Controller
@RequestMapping("process")
public class AllocateProjectsToStudentController extends BaseController {

    //private static final Logger logger = Logger.getLogger(AllocateProjectsToStudentController.class);

    /**
     * 本方法有两个功能，给学生分配题目和获取已分配的学生
     *
     * @param httpSession    当前会话，用于得到当前用户
     * @param modelMap       map集合，用于存储需要在jsp中获取的数据
     * @param ifSelected     根据是否选择，来区分是哪个功能，如果为true，则是获取已分配的学生
     * @param queryCondition 查询条件的集合
     * @param no             学生的学号，用于完成搜索功能
     * @param name           学生的名字，用于完成搜索功能
     * @param pageNo         当前页
     * @param pageSize       每页的数据
     * @return jsp
     */
    @RequestMapping(value = "/allocateProjectsToStudents.html", method = RequestMethod.GET)
    public String allocateProjects(HttpSession httpSession, ModelMap modelMap, String ifSelected, QueryCondition queryCondition, String no, String name, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        if (ifSelected == null) {
            Page<GraduateProject> graduateProjectCount = graduateProjectService.getPagesByProposerIfStudenSelected(tutor, true, pageNo, pageSize);
            modelMap.put("allocatedStudentCount", graduateProjectCount.getTotalElements());
        }
        Boolean selected = (ifSelected != null);
        //如果搜索的条件不为空，则添加到map中，在jsp的搜索框中列出当前的搜索项
        if (no != null) {
            modelMap.put("no", no);
        }
        if (name != null) {
            modelMap.put("name", name);
        }
        //根据学生是否已经选择课题来获得对应的学生
        List<Student> studentList = studentService.getPagesByTutor(tutor, selected, queryCondition.getQuery(no, name));
        //根据学生是否已选择课题来获取相应的课题
        List<GraduateProject> graduateProjectList = graduateProjectService.getAllProjectsByProposerIfStuentSelected(tutor, selected);
        //课题的数量
        modelMap.put("graduateProjectSize", graduateProjectList.size());
        modelMap.put("studentList", studentList);
        //学生的数量
        modelMap.put("studentSize", studentList.size());
        modelMap.put("actionUrl", httpServletRequest.getRequestURI());
        modelMap.put("graduateProjectList", graduateProjectList);
        //查询条件
        modelMap.put("queryCondition", queryCondition);
        //根据是否选择课题来跳转到不同的路径
        return selected ? "allocate/allocatedProjectsToStudent" : "allocate/allocateProjectsToStudent";
    }


    /**
     * 给学生分配题目的提交方法
     *
     * @param httpServletResponse 服务器对浏览器的响应，用于对浏览器写入json数据
     * @param studentId           需要分配的学生的id
     * @param graduateProjectId   需要分配的题目的id
     */
    @RequestMapping(value = "/allocateProjectsToStudents.html", method = RequestMethod.POST)
    public void allocateProjects(HttpServletResponse httpServletResponse, Integer studentId, Integer graduateProjectId) {
        //获取对应的学生
        Student student = studentService.findById(studentId);
        //如果学生已经选择了课题，则抛出异常
        //建议对该异常进行处理，不应该让用户看到错误的详细信息。只让用户看到一个错误提示框或是友好的错误页面就可以
        if (student.getGraduateProject() != null) {
            throw new MessageException("该学生已经选择课题，不能重复选题");
        }
        //获取对应的课题
        GraduateProject graduateProject = graduateProjectService.findById(graduateProjectId);
        //学生和课题进行双向关联
        graduateProject.setStudent(student);
        graduateProjectService.update(graduateProject);
        //对更新状态进行保存
        graduateProjectService.save(graduateProject);
        student.setGraduateProject(graduateProject);
        studentService.update(student);
        //对更新状态进行保存
        studentService.save(student);
        //判断该课题是否有主指导，没有设置为题目的申报者
        if (graduateProject.getMainTutorage() == null) {
            MainTutorage mainTutorage = new MainTutorage();
            //关联课题
            mainTutorage.setGraduateProject(graduateProject);
            mainTutorage.setTutor(graduateProject.getProposer());
            mainTutorageService.save(mainTutorage);
            //获取唯一的结果
            //建议对方法语句进行异常处理
            mainTutorage = mainTutorageService.uniqueResult("graduateProject", GraduateProject.class, graduateProject);
            //对当前课题设置主指导
            graduateProject.setMainTutorage(mainTutorage);
            //更新数据库的信息
            graduateProjectService.update(graduateProject);
            //对更新状态进行保存
            graduateProjectService.save(graduateProject);
        }

        //给浏览器返回json数据
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    /**
     * 取消学生和课题的关联
     *
     * @param httpServletResponse 服务器对浏览器的响应，用于向浏览器写入json数据
     * @param graduateProjectId   需要被需要取消的课题的id
     */
    @RequestMapping(value = "/cancelGraduateProject.html", method = RequestMethod.POST)
    public void cancelAllocatedProject(HttpServletResponse httpServletResponse, Integer graduateProjectId) {
        //根据id获取要取消匹配的课题
        GraduateProject cancelGraduateProject = graduateProjectService.findById(graduateProjectId);
        //获取与课题匹配的学生
        Student student = cancelGraduateProject.getStudent();
        //取消学生与课题的关联
        student.setGraduateProject(null);
        //更新数据库
        studentService.saveOrUpdate(student);
        //取消课题与学生的关联
        cancelGraduateProject.setStudent(null);
        //更新数据库
        graduateProjectService.saveOrUpdate(cancelGraduateProject);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }


}
