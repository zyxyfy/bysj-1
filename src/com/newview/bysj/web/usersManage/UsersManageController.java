package com.newview.bysj.web.usersManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.Major;
import com.newview.bysj.domain.School;
import com.newview.bysj.domain.StudentClass;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
@RequestMapping("usersManage")
public class UsersManageController extends BaseController {

    private static final Logger logger = Logger.getLogger(UsersManageController.class);

    @RequestMapping("/universityAdmin/role")
    public String list(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        //跳转到相应的jsp进行角色判断
        return "usersManage/universityAdmin/redirectUniversityAdminToRole";
    }

    //根据教研室主任获取专业
    @RequestMapping("/department/getTitle")
    public String getTitleByDepartment(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Page<Major> majors = majorService.getMajorByDepartment(employee.getDepartment(), pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, majors, "majors", CommonHelper.getRequestUrl(httpServletRequest), majors.getTotalElements());
        //获取教研室主任所在教研室的id
        modelMap.addAttribute("departmentId", employee.getDepartment().getId());
        return "usersManage/universityAdmin/listMajor";
    }

    //根据院级管理员获取教研室
    @RequestMapping("/school/getTitle")
    public String getTitleBySchool(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Page<Department> departments = departmentService.getDepartmentBySchool(employee.getDepartment().getSchool(), pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, departments, "departments", CommonHelper.getRequestUrl(httpServletRequest), departments.getTotalElements());
        //获取院级管理员所在学院的id
        modelMap.addAttribute("schoolId", employee.getDepartment().getSchool().getId());
        return "usersManage/universityAdmin/listDepartment";
    }

    //根据校级管理员获取学院
    @RequestMapping("/college/getTitle")
    public String getTitleByCollege(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Page<School> school = schoolService.pageQuery(pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, school, "schools", CommonHelper.getRequestUrl(httpServletRequest), school.getTotalElements());
        return "usersManage/universityAdmin/listSchool";
    }

    //添加学院
    @RequestMapping(value = "/addSchool.html", method = RequestMethod.GET)
    public String addSchool(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditSchool";
    }

    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    public String addSchool(ModelMap modelMap, HttpServletResponse httpServletResponse, String description) {
        School school = new School();
        school.setDescription(description);
        schoolService.save(school);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/college/getTitle.html";
    }

    //添加教研室
    @RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
    public String addDepartment(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer schoolId) {
        modelMap.addAttribute("school", schoolService.findById(schoolId));
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditDepartment";
    }

    @RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
    public String addDepartment(ModelMap modelMap, HttpServletResponse httpServletResponse, String description, Integer schoolId) {
        School school = schoolService.findById(schoolId);
        Department department = new Department();
        department.setDescription(description);
        department.setSchool(school);
        departmentService.save(department);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/listDepartment.html?schoolId=" + schoolId;
    }

    //添加专业
    @RequestMapping(value = "/addMajor", method = RequestMethod.GET)
    public String addMajor(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer departmentId) {
        modelMap.addAttribute("department", departmentService.findById(departmentId));
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditMajor";
    }

    @RequestMapping(value = "/addMajor", method = RequestMethod.POST)
    public String addMajor(ModelMap modelMap, HttpServletResponse httpServletResponse, String description, Integer departmentId) {
        Major major = new Major();
        major.setDescription(description);
        major.setDepartment(departmentService.findById(departmentId));
        majorService.save(major);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/listMajor.html?departmentId=" + departmentId;
    }

    //添加班级
    @RequestMapping(value = "/addStudentClass", method = RequestMethod.GET)
    public String addClass(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer majorId) {
        modelMap.addAttribute("major", majorService.findById(majorId));
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditStudentClass";
    }

    @RequestMapping(value = "/addStudentClass", method = RequestMethod.POST)
    public String addStudentClass(HttpServletResponse httpServletResponse, ModelMap modelMap, String description, Integer majorId) {
        StudentClass studentClass = new StudentClass();
        studentClass.setDescription(description);
        studentClass.setMajor(majorService.findById(majorId));
        studentClassService.save(studentClass);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/listStudentClass.html?majorId=" + majorId;
    }

    //修改学院
    @RequestMapping(value = "/editSchool", method = RequestMethod.GET)
    public String editSchool(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer schoolId) {
        School school = schoolService.findById(schoolId);
        modelMap.addAttribute("school", school);
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditSchool";
    }

    @RequestMapping(value = "/editSchool", method = RequestMethod.POST)
    public String editSchool(HttpServletResponse httpServletResponse, Integer editId, String description) {
        School school = schoolService.findById(editId);
        school.setDescription(description);
        schoolService.saveOrUpdate(school);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/college/getTitle.html";
    }

    //修改教研室
    @RequestMapping(value = "/editDepartment", method = RequestMethod.GET)
    public String editDepartment(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer departmentId, Integer schoolId) {
        Department department = departmentService.findById(departmentId);
        modelMap.addAttribute("school", schoolService.findById(schoolId));
        modelMap.addAttribute("department", department);
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditDepartment";
    }

    @RequestMapping(value = "/editDepartment", method = RequestMethod.POST)
    public String editDepartment(HttpServletResponse httpServletResponse, Integer schoolId, Integer editId, String description) {
        Department department = departmentService.findById(editId);
        department.setDescription(description);
        departmentService.saveOrUpdate(department);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/listDepartment.html?schoolId=" + schoolId;
    }

    //修改专业
    @RequestMapping(value = "/editMajor", method = RequestMethod.GET)
    public String editMajor(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer majorId, Integer departmentId) {
        Major major = majorService.findById(majorId);
        modelMap.addAttribute("major", major);
        modelMap.addAttribute("department", departmentService.findById(departmentId));
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));

        return "usersManage/universityAdmin/addorEditMajor";
    }

    @RequestMapping(value = "/editMajor", method = RequestMethod.POST)
    public String editMajor(HttpServletResponse httpServletResponse, Integer editId, Integer departmentId, String description, HttpServletRequest request) {
        Major major = majorService.findById(editId);
        major.setDescription(description);
        majorService.update(major);
        //对更新状态进行保存
        majorService.save(major);
        return "redirect:/usersManage/listMajor.html?departmentId=" + departmentId;
    }

    //修改班级
    @RequestMapping(value = "/editStudentClass", method = RequestMethod.GET)
    public String editStudentClass(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer studentClassId, Integer majorId) {
        StudentClass studentClass = studentClassService.findById(studentClassId);
        modelMap.addAttribute("studentClass", studentClass);
        modelMap.addAttribute("major", majorService.findById(majorId));
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(httpServletRequest));
        return "usersManage/universityAdmin/addorEditStudentClass";
    }

    @RequestMapping(value = "/editStudentClass", method = RequestMethod.POST)
    public String editStudentClass(HttpServletResponse httpServletResponse, Integer editId, Integer majorId, String description) {
        StudentClass studentClass = studentClassService.findById(editId);
        studentClass.setDescription(description);
        studentClassService.update(studentClass);
        //对更新状态进行保存
        studentClassService.save(studentClass);
        return "redirect:/usersManage/listStudentClass.html?majorId=" + majorId;
    }

    //查看教研室
    @RequestMapping("/listDepartment")
    public String listDepartment(ModelMap modelMap, HttpServletRequest httpServletRequest, Integer schoolId, Integer pageNo, Integer pageSize) {
        School school = schoolService.findById(schoolId);
        Page<Department> department = departmentService.getDepartmentBySchool(school, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, department, "departments", CommonHelper.getRequestUrl(httpServletRequest), department.getTotalElements());
        modelMap.addAttribute("schoolId", schoolId);
        return "usersManage/universityAdmin/listDepartment";
    }

    //查看专业
    @RequestMapping("/listMajor")
    public String listMajor(ModelMap modelMap, HttpServletRequest httpServletRequest, Integer departmentId, Integer pageNo, Integer pageSize) {
        Department department = departmentService.findById(departmentId);
        modelMap.addAttribute("majors", department.getMajor());
        modelMap.addAttribute("departmentId", departmentId);
        return "usersManage/universityAdmin/listMajor";
    }

    //查看班级
    @RequestMapping("/listStudentClass")
    public String listStudentClass(ModelMap modelMap, HttpServletRequest httpServletRequest, Integer majorId, Integer pageNo, Integer pageSize) {
        Major major = majorService.findById(majorId);
        Page<StudentClass> studentClass = studentClassService.getStudentClassByMajor(major, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, studentClass, "studentClasses", CommonHelper.getRequestUrl(httpServletRequest), studentClass.getTotalElements());
        modelMap.addAttribute("majorId", majorId);
        return "usersManage/universityAdmin/listStudentClass";
    }

    //查询学院
    @RequestMapping(value = "/selectSchool")
    public String selectSchool(HttpServletRequest httpServletRequest, ModelMap modelMap, String description, Integer pageNo, Integer pageSize) {
        Page<School> schools = schoolService.pageQuery(pageNo, pageSize, description);
        CommonHelper.pagingHelp(modelMap, schools, "schools", CommonHelper.getRequestUrl(httpServletRequest), schools.getTotalElements());
        modelMap.put("description", description);
        return "usersManage/universityAdmin/listSchool";
    }

    //删除学院
    @RequestMapping(value = "/deleteSchool", method = RequestMethod.GET)
    public void deleteSchool(HttpServletResponse httpServletResponse, Integer schoolId) {
        School school = schoolService.findById(schoolId);
        if (school.getDepartment().isEmpty()) {
            schoolService.deleteObject(school);
        }
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //删除教研室
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.GET)
    public void deleteDepartment(HttpServletResponse httpServletResponse, Integer departmentId) {
        Department department = departmentService.findById(departmentId);
        if (department.getMajor().isEmpty()) {
            departmentService.deleteObject(department);
        }
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //删除专业
    @RequestMapping(value = "/deleteMajor", method = RequestMethod.GET)
    public void deleteMajor(HttpServletResponse httpServletResponse, Integer majorId) {
        Major major = majorService.findById(majorId);
        if (major.getStudentClass().isEmpty()) {
            majorService.deleteObject(major);
        }
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    //删除班级
    @RequestMapping(value = "/deleteStudentClass", method = RequestMethod.GET)
    public void deleteStudentClass(HttpServletResponse httpServletResponse, Integer studentClassId) {
        StudentClass studentClass = studentClassService.findById(studentClassId);
        studentClassService.deleteObject(studentClass);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
}
