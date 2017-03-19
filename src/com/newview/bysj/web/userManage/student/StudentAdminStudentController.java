package com.newview.bysj.web.userManage.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newview.bysj.domain.Contact;
import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.User;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
@RequestMapping("usersManage")
public class StudentAdminStudentController extends BaseController {
    @RequestMapping("/student")
    public String list(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageSize);
        modelMap.put("pageSize", pageSize);
        //根据不同的角色跳转到不同的jsp
        return "usersManage/student/redirectStudentManage";
    }

    //最高角色是教研室主任
    @RequestMapping("/department/student")
    public String departmentStudent(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        //获取该用户教研室下的所有学生
        Page<Student> students = studentService.getStudentByDepartment(employee.getDepartment().getId(), pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, students, "studentList", httpServletRequest.getRequestURI(), students.getTotalElements());
        //modelMap.addAttribute("studentClassList", employee.getDepartment().getMajor());
        //为了实现修改时的地址跳转
        modelMap.addAttribute("edit", "department");
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/department/searchStudent.html");
        //为了列出查询所需的班级
        modelMap.addAttribute("majorList", employee.getDepartment().getMajor());
        return "usersManage/student/studentList";
    }

    //最高角色是院级管理员
    @RequestMapping("/school/student")
    public String schoolStudent(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        //获取该用户学院下的所有学生
        Page<Student> students = studentService.getStudentBySchool(employee.getDepartment().getSchool().getId(), pageNo, pageSize);
        //CommonHelper.paging(modelMap,students,"studentList");
        CommonHelper.pagingHelp(modelMap, students, "studentList", httpServletRequest.getRequestURI(), students.getTotalElements());
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/school/searchStudent.html");
        //为了实现修改时的地址跳转
        modelMap.addAttribute("edit", "school");
        return "usersManage/student/studentList";
    }

    //最高角色是校级管理员
    @RequestMapping("/college/student")
    public String collegeStudent(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        //获取该用户学校的所有学生
        Page<Student> students = studentService.getStudentByCollege(pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, students, "studentList", httpServletRequest.getRequestURI(), students.getTotalElements());
        modelMap.put("schoolList", schoolService.findAll());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/college/searchStudent.html");
        //为了实现修改时的地址跳转
        modelMap.addAttribute("edit", "college");
        return "usersManage/student/studentList";
    }

    //教研室主任查询
    @RequestMapping(value = "/department/searchStudent.html", method = RequestMethod.GET)
    public String searchStudentByDepartment(HttpSession httpSession, ModelMap modelMap, HttpServletRequest httpServletRequest,
                                            String no, String name, Integer studentClassId, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Integer departmentId = employee.getDepartment().getId();
        Integer schoolId = employee.getDepartment().getSchool().getId();
        Page<Student> students = searchStudentList(employee, no, name, schoolId, departmentId, studentClassId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, students, "studentList", CommonHelper.getRequestUrl(httpServletRequest), students.getTotalElements());
        //为了列出查询所需的班级
        modelMap.addAttribute("majorList", employee.getDepartment().getMajor());
        //添加检索需要的属性
        modelMap.put("no", no);
        modelMap.put("name", name);
        modelMap.put("studentClassId", studentClassId);
        return "usersManage/student/studentList";
    }

    //院级管理员查询
    @RequestMapping(value = "/school/searchStudent.html", method = RequestMethod.GET)
    public String searchStudentBySchool(HttpSession httpSession, ModelMap modelMap, HttpServletRequest httpServletRequest,
                                        String no, String name, Integer departmentSelect, Integer studentClassSelect, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Integer schoolId = employee.getDepartment().getSchool().getId();
        Page<Student> students = searchStudentList(employee, no, name, schoolId, departmentSelect, studentClassSelect, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, students, "studentList", CommonHelper.getRequestUrl(httpServletRequest), students.getTotalElements());
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        //添加检索需要的属性
        modelMap.put("no", no);
        modelMap.put("name", name);
        modelMap.put("departmentSelect", departmentSelect);
        modelMap.put("studentClassSelect", studentClassSelect);
        return "usersManage/student/studentList";
    }

    //校级管理员查询
    @RequestMapping(value = "/college/searchStudent.html", method = RequestMethod.GET)
    public String searchStudentByCollege(HttpSession httpSession, ModelMap modelMap, HttpServletRequest httpServletRequest,
                                         String name, String no, Integer schoolSelect, Integer departmentSelect, Integer studentClassSelect, Integer majorId, Integer pageNo, Integer pageSize) {

        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Page<Student> students = searchStudentList(employee, no, name, schoolSelect, departmentSelect, studentClassSelect, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, students, "studentList", CommonHelper.getRequestUrl(httpServletRequest), students.getTotalElements());
        //为实现多次查询的功能
        modelMap.put("schoolList", schoolService.findAll());
        //添加检索需要的属性
        modelMap.put("no", no);
        modelMap.put("name", name);
        modelMap.put("schoolSelect", schoolSelect);
        modelMap.put("departmentSelect", departmentSelect);
        modelMap.put("studentClassSelect", studentClassSelect);
        modelMap.put("majorId", majorId);
        return "usersManage/student/studentList";
    }

    //教研室主任添加学生
    @RequestMapping(value = "/department/studentAdd.html", method = RequestMethod.GET)
    public String addStudentByDepartment(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        prepareAddModel(request, modelMap, employee);
        return "usersManage/student/addOrEditStudent";

    }

    //院级管理员添加
    @RequestMapping(value = "/school/studentAdd.html", method = RequestMethod.GET)
    public String addStudentBySchool(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        prepareAddModel(request, modelMap, employee);
        return "usersManage/student/addOrEditStudent";
    }

    //校级管理员添加
    @RequestMapping(value = "/college/studentAdd.html", method = RequestMethod.GET)
    public String addStudentByCollege(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        prepareAddModel(request, modelMap, employee);
        return "usersManage/student/addOrEditStudent";
    }

    @RequestMapping(value = "/department/studentAdd.html", method = RequestMethod.POST)
    public String departmentAddStudent(@ModelAttribute("student") Student student, Integer studentClassSelect) {
        addOrEditStudent(student, studentClassSelect);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/student.html";
    }

    @RequestMapping(value = "/school/studentAdd.html", method = RequestMethod.POST)
    public String schoolAddStudent(@ModelAttribute("student") Student student, Integer studentClassSelect) {
        addOrEditStudent(student, studentClassSelect);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/student.html";
    }

    @RequestMapping(value = "/college/studentAdd.html", method = RequestMethod.POST)
    public String collegeAddStudent(@ModelAttribute("student") Student student, Integer studentClassSelect) {
        addOrEditStudent(student, studentClassSelect);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/student.html";
    }

    //教研室主任修改学生
    @RequestMapping("/department/studentEdit.html")
    public String editStudentByDepartment(HttpServletRequest request, ModelMap modelMap, Integer studentId) {
        Student student = studentService.findById(studentId);
        prepareEditModel(request, modelMap, student);
        return "usersManage/student/addOrEditStudent";
    }

    //院级管理员修改学生
    @RequestMapping("/school/studentEdit.html")
    public String editStudentBySchool(HttpServletRequest request, ModelMap modelMap, Integer studentId) {
        Student student = studentService.findById(studentId);
        prepareEditModel(request, modelMap, student);
        return "usersManage/student/addOrEditStudent";
    }

    //校级管理员修改学生
    @RequestMapping("/college/studentEdit.html")
    public String editStudentByCollege(HttpServletRequest request, ModelMap modelMap, Integer studentId) {
        Student student = studentService.findById(studentId);
        prepareEditModel(request, modelMap, student);
        return "usersManage/student/addOrEditStudent";
    }

    @RequestMapping(value = "/department/studentEdit.html", method = RequestMethod.POST)
    public String editStudentByDepartment(HttpServletResponse httpServletResponse, Student student, Integer studentClassSelect) {
        addOrEditStudent(student, studentClassSelect);
        return "redirect:/usersManage/student.html";

    }

    @RequestMapping(value = "/school/studentEdit.html", method = RequestMethod.POST)
    public String editStudentBySchool(HttpServletResponse httpServletResponse, Student student, Integer studentClassSelect) {
        addOrEditStudent(student, studentClassSelect);
        return "redirect:/usersManage/student.html";
    }

    @RequestMapping(value = "/college/studentEdit.html", method = RequestMethod.POST)
    public String editStudentByCollege(HttpServletResponse httpServletResponse, Student student, Integer studentClassSelect) {
        addOrEditStudent(student, studentClassSelect);
        return "redirect:/usersManage/student.html";

    }

    public void prepareEditModel(HttpServletRequest request, ModelMap modelMap, Student student) {
        modelMap.addAttribute("student", student);

        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(request));
        modelMap.addAttribute("schoolList", schoolService.findAll());
    }

    public void addOrEditStudent(Student student, Integer studentClassId) {
        Student studentReady = null;
        if (student.getId() == null) {
            studentReady = student;
            studentService.saveStudent(studentReady);
            //获取保存的studentReady否则更新studentReady会出错
            studentReady = studentService.uniqueResult("no", student.getNo());

        } else {
            studentReady = studentService.findById(student.getId());

            studentReady.setName(student.getName());
            studentReady.setNo(student.getNo());
            studentReady.setSex(student.getSex());

            if (studentReady.getContact() == null) {
                Contact contact = new Contact();
                contact.setEmail(student.getContact().getEmail());
                contact.setMoblie(student.getContact().getMoblie());
                contact.setQq(student.getContact().getQq());
                studentReady.setContact(contact);
            } else {
                studentReady.getContact().setEmail(student.getContact().getEmail());
                studentReady.getContact().setMoblie(student.getContact().getMoblie());
                studentReady.getContact().setQq(student.getContact().getQq());
            }
        }
        studentReady.setStudentClass(studentClassService.findById(studentClassId));
        studentService.saveOrUpdate(studentReady);
    }

    public void prepareAddModel(HttpServletRequest request, ModelMap modelMap, Employee employee) {
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(request));
        System.out.println("---------" + CommonHelper.getRequestUrl(request));
        modelMap.addAttribute("schoolList", schoolService.findAll());
        modelMap.addAttribute("student", new Student());
    }

    public Page<Student> searchStudentList(Employee employee, String studentNo, String studentName, Integer schoolId, Integer departmentId, Integer classId, Integer pageNo, Integer pageSize) {
        Page<Student> students = null;
        if (schoolId == null || schoolId == 0) schoolId = 0;
        if (departmentId == null || departmentId == 0) departmentId = 0;
        //if(majorId == null||majorId ==0) majorId = 0;
        if (classId == null || classId == 0) classId = 0;
        if (schoolId == 0) {
            students = studentService.getStudents(studentNo, studentName, pageNo, pageSize);
        } else {
            if (classId != 0) {
                students = studentService.getStudentByStudentClass(studentNo, studentName, classId, pageNo, pageSize);
            } else {
                if (departmentId != 0) {
                    students = studentService.getStudentByDepartment(studentNo, studentName, departmentId, pageNo, pageSize);
                } else {
                    students = studentService.getStudentBySchool(studentNo, studentName, schoolId, pageNo, pageSize);
                }
            }
        }
        return students;
    }

    //删除学生
    @RequestMapping("/deleteStudent.html")
    public void deleteStudent(HttpServletResponse httpServletResponse, Integer studentId) {
        Student student = studentService.findById(studentId);
        studentService.deleteStudent(student);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //重置密码
    @RequestMapping("/resetPassword")
    public void resetPassword(HttpServletResponse httpServletResponse, Integer studentId) {
        Student student = studentService.findById(studentId);
        User user = student.getUser();
        user.setPassword(CommonHelper.makeMD5(student.getNo()));
        userService.saveOrUpdate(user);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

}
