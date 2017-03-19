package com.newview.bysj.web.taskDocManage;

import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.TaskDoc;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class StudentTaskDocController extends BaseController {
    //学生查看任务书
    @RequestMapping("student/lookUpTaskDoc")
    public String lookUpTaskDoc(HttpSession httpSession, ModelMap modelMap) {
        //获得当前用户
        Student student = studentService.findById(((Student) CommonHelper.getCurrentActor(httpSession)).getId());
        if (student.getGraduateProject() == null) {
            modelMap.addAttribute("message", "未选择课题，请联系指导老师");
        } else if (student.getGraduateProject().getTaskDoc() != null) {
            modelMap.addAttribute("graduateProject", student.getGraduateProject());
            modelMap.addAttribute("studentClass", student.getStudentClass());
        } else {
            modelMap.addAttribute("message", "未下达任务书，请联系指导老师");
            return "student/openningReport/noOpenningReport";
        }
        return "student/taskDoc/lookUpTaskDoc";
    }

    //下载任务书
    @RequestMapping("student/download/taskDoc")
    public ResponseEntity<byte[]> downLoad(HttpSession httpSession, Integer taskDocId) throws IOException {
        Student student = (Student) CommonHelper.getCurrentActor(httpSession);
        TaskDoc taskDoc = taskDocService.findById(taskDocId);
        String url = taskDoc.getUrl();
        File file = new File(url);
        String fileName = "任务书-" + student.getNo() + "-" + student.getName() + "-" + student.getGraduateProject().getTitle() + "-";
        if (student.getGraduateProject().getSubTitle() != null) {
            fileName = fileName + student.getGraduateProject().getSubTitle();
        } else {
            fileName = fileName + "无";
        }
        String fileExtention = file.getName().substring(file.getName().lastIndexOf("."));
        return CommonHelper.download(httpSession, taskDoc.getUrl(), fileName + fileExtention);
    }
    //private static final Logger logger = Logger.getLogger(StudentTaskDocController.class);
}
