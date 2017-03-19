package com.newview.bysj.web.userManage.employee;

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
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.domain.VisitingEmployee;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
@RequestMapping("usersManage")
public class VisitingAdminEmployeeController extends BaseController {
    @RequestMapping("/visitingEmployee")
    public String list(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        return "usersManage/visitingEmployee/redirectVisitingEmployee";
    }

    //最高角色是教研室主任
    @RequestMapping("/department/visitingEmployee")
    public String departmentEmployee(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        //获取当前用户教研室下的校外职工
        Page<VisitingEmployee> visitingEmployee = visitingEmployeeService.getVisitingEmployeeByDepartment(employee.getDepartment().getId(), pageNo, pageSize);
        CommonHelper.paging(modelMap, visitingEmployee, "visitingEmployees");

        modelMap.addAttribute("departments", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("schools", schoolService.findAll());
        modelMap.addAttribute("departmentId", employee.getDepartment().getId());
        modelMap.addAttribute("schoolId", employee.getDepartment().getSchool().getId());

        modelMap.addAttribute("totalDescription", "employeeList");
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/department/searchVisitingEmployee.html");
        modelMap.addAttribute("edit", "department");
        return "/usersManage/visitingEmployee/visitingEmployeeList";
    }

    //最高角色是院级管理员
    @RequestMapping("/school/visitingEmployee")
    public String schoolEmployee(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        //获得当前用户学院的校外职工
        Page<VisitingEmployee> visitingEmployee = visitingEmployeeService.getVisitingEmployeeBySchool(pageNo, pageSize, employee.getDepartment().getSchool().getId());
        CommonHelper.paging(modelMap, visitingEmployee, "visitingEmployees");

        modelMap.addAttribute("departments", employee.getDepartment().getSchool().getDean());
        modelMap.addAttribute("schools", schoolService.findAll());
        modelMap.addAttribute("showDepartment", "1");
        modelMap.addAttribute("schoolId", employee.getDepartment().getSchool().getId());

        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/school/searchVisitingEmployee.html");
        modelMap.addAttribute("edit", "school");
        return "/usersManage/visitingEmployee/visitingEmployeeList";
    }

    //最高角色是校级管理员
    @RequestMapping(value = "/college/visitingEmployee", method = RequestMethod.GET)
    public String collegeEmployee(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户学校的校外职工
        Page<VisitingEmployee> visitingEmployee = visitingEmployeeService.getAll(pageNo, pageSize);
        //分页
        CommonHelper.pagingHelp(modelMap, visitingEmployee, "visitingEmployees", CommonHelper.getRequestUrl(httpServletRequest), visitingEmployee.getTotalElements());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/college/searchVisitingEmployee.html");
        modelMap.addAttribute("edit", "college");
        return "/usersManage/visitingEmployee/visitingEmployeeList";

    }

    //教研室主任查询
    @RequestMapping(value = "/department/searchVisitingEmployee.html", method = RequestMethod.POST)
    public String searchEmployeeByDepartment(HttpSession httpSession, ModelMap modelMap, @ModelAttribute("employeeNo") String employeeNo, @ModelAttribute("employeeName") String employeeName,
                                             Integer pageNo, Integer pageSize) {
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Integer departmentId = employee.getDepartment().getId();
        Integer schoolId = employee.getDepartment().getSchool().getId();
        Page<VisitingEmployee> employees = searchVisitingEmployee(employee, employeeNo, employeeName, departmentId, schoolId, pageNo, pageSize);
        CommonHelper.paging(modelMap, employees, "visitingEmployee");

        modelMap.addAttribute("totalDescription", "employeeList");
        modelMap.addAttribute("actionUrl", "/usersManage/department/searchVisitingEmployee.html");
        modelMap.addAttribute("postActionUrl", "/usersManage/department/searchVisitingEmployee.html");
        return "/userManage/visitingEmployee/visitingAdminEmployList";
    }

    //院级管理员查询
    @RequestMapping(value = "/school/searchVisitingEmployee.html", method = RequestMethod.POST)
    public String searchEmployeeBySchool(HttpSession httpSession, ModelMap modelMap, @ModelAttribute("employeeNo") String employeeNo,
                                         @ModelAttribute("employeeName") String employeeName, @ModelAttribute("departmentId") Integer departmentId,
                                         Integer pageNo, Integer pageSize) {
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Integer schoolId = employee.getDepartment().getSchool().getId();
        Page<VisitingEmployee> employees = searchVisitingEmployee(employee, employeeNo, employeeName, departmentId, schoolId, pageNo, pageSize);
        CommonHelper.paging(modelMap, employees, "visitingEmployee");

        modelMap.addAttribute("departments", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("totalDescription", "employeeList");

        modelMap.addAttribute("actionUrl", "/usersManage/school/searchVisitingEmployee.html");
        modelMap.addAttribute("postActionUrl", "/usersManage/school/searchVisitingEmployee.html");
        return "/userManage/visitingEmployee/visitingAdminEmployList";
    }

    //校级管理员查询
    @RequestMapping(value = "/college/searchVisitingEmployee.html", method = RequestMethod.POST)
    public String searchEmployeeByCollege(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, String no, String name, Integer departmentId, Integer schoolId, Integer pageNo, Integer pageSize) {
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Page<VisitingEmployee> employees = searchVisitingEmployee(employee, no, name, departmentId, schoolId, pageNo, pageSize);
        //分页
        CommonHelper.pagingHelp(modelMap, employees, "visitingEmployees", CommonHelper.getRequestUrl(httpServletRequest), employees.getTotalElements());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        return "/usersManage/visitingEmployee/visitingEmployeeList";
    }

    public Page<VisitingEmployee> searchVisitingEmployee(Employee employee, String no, String name, Integer departmentId, Integer schoolId, Integer pageNo, Integer pageSize) {
        Page<VisitingEmployee> employees = null;
        if (name == "")
            name = null;
        if (no == "")
            no = null;
        if (schoolId == null)
            schoolId = employee.getDepartment().getSchool().getId();
        if (departmentId == null)
            departmentId = employee.getDepartment().getId();
        if (schoolId == 0) {
            employees = visitingEmployeeService.getVisitingEmployees(no, name, pageNo, pageSize);
        } else {
            if (departmentId == 0) {
                if (no == null && name == null) {
                    employees = visitingEmployeeService.getVisitingEmployeeBySchool(schoolId, pageNo, pageSize);
                } else {
                    employees = visitingEmployeeService.getVisitingEmployeeBySchool(pageNo, pageSize, no, name, schoolId);
                }
            } else {
                if (no == null && name == null) {
                    employees = visitingEmployeeService.getVisitingEmployeeByDepartment(departmentId, pageNo, pageSize);
                } else {
                    employees = visitingEmployeeService.getVisitingEmployeeByDepartment(pageNo, pageSize, no, name, departmentId);
                }
            }
        }
        return employees;
    }

    //教研室主任添加
    @RequestMapping(value = "/department/visitingEmployeeAdd.html", method = RequestMethod.GET)
    public String departmentAddEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        modelMap.addAttribute("visitingEmployee", new VisitingEmployee());
        prepareModel(request, modelMap);
        return "usersManage/visitingEmployee/addOrEditVisitingEmployee";
    }

    //院级管理员添加
    @RequestMapping(value = "/school/visitingEmployeeAdd.html", method = RequestMethod.GET)
    public String schoolAddEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        modelMap.addAttribute("visitingEmployee", new VisitingEmployee());
        prepareModel(request, modelMap);
        return "usersManage/visitingEmployee/addOrEditVisitingEmployee";
    }

    //校级管理员添加
    @RequestMapping(value = "/college/visitingEmployeeAdd.html", method = RequestMethod.GET)
    public String collegeAddEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        modelMap.addAttribute("visitingEmployee", new VisitingEmployee());
        prepareModel(request, modelMap);
        return "usersManage/visitingEmployee/addOrEditVisitingEmployee";
    }

    @RequestMapping(value = "/department/visitingEmployeeAdd.html", method = RequestMethod.POST)
    public String departmentAddEmployee(HttpServletResponse httpServletResponse, @ModelAttribute("visitingEmployee") VisitingEmployee visitingEmployee, Integer departmentId) {
        addOrEditVisitingEmployee(visitingEmployee, departmentId);
        return "redirect:/usersManage/visitingEmployee.html";
    }

    @RequestMapping(value = "/school/visitingEmployeeAdd.html", method = RequestMethod.POST)
    public String schoolAddEmployee(HttpServletResponse httpServletResponse, @ModelAttribute("visitingEmployee") VisitingEmployee visitingEmployee, Integer departmentId) {
        addOrEditVisitingEmployee(visitingEmployee, departmentId);
        return "redirect:/usersManage/visitingEmployee.html";
    }

    @RequestMapping(value = "/college/visitingEmployeeAdd.html", method = RequestMethod.POST)
    public String collegeAddEmployee(HttpServletResponse httpServletResponse, VisitingEmployee visitingEmployee, Integer departmentId) {
        addOrEditVisitingEmployee(visitingEmployee, departmentId);
        return "redirect:/usersManage/visitingEmployee.html";
    }

    //教研室主任修改
    @RequestMapping(value = "/department/visitingEmployeeEdit.html", method = RequestMethod.GET)
    public String departmentEditVisitingEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap, Integer visitingEmployeeId) {
        VisitingEmployee visitingEmployee = visitingEmployeeService.findById(visitingEmployeeId);
        modelMap.addAttribute("visitingEmployee", visitingEmployee);
        modelMap.addAttribute("departmentList", visitingEmployee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("currentDepartment", visitingEmployee.getDepartment());
        modelMap.addAttribute("edited", true);
        prepareModel(request, modelMap);
        return "usersManage/visitingEmployee/addOrEditVisitingEmployee";
    }

    //院级管理员修改
    @RequestMapping(value = "/school/visitingEmployeeEdit.html", method = RequestMethod.GET)
    public String schoolEditVisitingEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap, Integer visitingEmployeeId) {
        VisitingEmployee visitingEmployee = visitingEmployeeService.findById(visitingEmployeeId);
        modelMap.addAttribute("visitingEmployee", visitingEmployee);
        modelMap.addAttribute("departmentList", visitingEmployee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("currentDepartment", visitingEmployee.getDepartment());
        modelMap.addAttribute("edited", true);
        prepareModel(request, modelMap);
        return "usersManage/visitingEmployee/addOrEditVisitingEmployee";
    }

    //校级管理员修改
    @RequestMapping(value = "/college/visitingEmployeeEdit.html", method = RequestMethod.GET)
    public String collegeEditVisitingEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap, Integer visitingEmployeeId) {
        VisitingEmployee visitingEmployee = visitingEmployeeService.findById(visitingEmployeeId);
        modelMap.addAttribute("visitingEmployee", visitingEmployee);
        modelMap.addAttribute("departmentList", visitingEmployee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("currentDepartment", visitingEmployee.getDepartment());
        modelMap.addAttribute("edited", true);
        prepareModel(request, modelMap);
        return "usersManage/visitingEmployee/addOrEditVisitingEmployee";
    }

    @RequestMapping(value = "/department/visitingEmployeeEdit.html", method = RequestMethod.POST)
    public String departmentEditVisitingEmployee(HttpServletResponse httpServletResponse, @ModelAttribute("visitingEmployee") VisitingEmployee visitingEmployee, Integer departmentId) {
        addOrEditVisitingEmployee(visitingEmployee, departmentId);
        return "redirect:/usersManage/visitingEmployee.html";
    }

    @RequestMapping(value = "/school/visitingEmployeeEdit.html", method = RequestMethod.POST)
    public String schoolEditVisitingEmployee(HttpServletResponse httpServletResponse, @ModelAttribute("visitingEmployee") VisitingEmployee visitingEmployee, Integer departmentId) {
        addOrEditVisitingEmployee(visitingEmployee, departmentId);
        return "redirect:/usersManage/visitingEmployee.html";
    }

    @RequestMapping(value = "/college/visitingEmployeeEdit.html", method = RequestMethod.POST)
    public String collegeEditVisitingEmployee(HttpServletResponse httpServletResponse, @ModelAttribute("visitingEmployee") VisitingEmployee visitingEmployee, Integer departmentId) {
        addOrEditVisitingEmployee(visitingEmployee, departmentId);
        return "redirect:/usersManage/visitingEmployee.html";
    }

    public void prepareModel(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("degree", degreeService.findAll());
        modelMap.addAttribute("proTitle", proTitleService.findAll());
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(request));
        modelMap.addAttribute("schoolList", schoolService.findAll());
    }

    public void addOrEditVisitingEmployee(VisitingEmployee visitingEmployee, Integer departmentId) {
        VisitingEmployee visitingEmployeeReady = null;
        if (visitingEmployee.getId() == null) {
            visitingEmployeeReady = visitingEmployee;
            if (visitingEmployeeReady.getProTitle() != null) {
                //visitingEmployee.getProTitle()获取的对象不是从数据库中得到的所以需要重新获取数据库中对应的proTitle否则保存visitingEmployee会出错
                visitingEmployeeReady.setProTitle(proTitleService.uniqueResult("description", visitingEmployee.getProTitle().getDescription()));
            } else {
                visitingEmployeeReady.setProTitle(null);
            }
            if (visitingEmployeeReady.getDegree() != null) {
                //visitingEmployee.getDegree()获取的对象不是从数据库中得到的所以需要重新获取数据库中对应的degree否则保存visitingEmployee会出错
                visitingEmployeeReady.setDegree(degreeService.uniqueResult("description", visitingEmployee.getDegree().getDescription()));
            } else {
                visitingEmployeeReady.setDegree(null);
            }
            visitingEmployeeReady = visitingEmployee;
            //为visitingEmployee设置登陆用户
            User user = new User();
            user.setActor(visitingEmployeeReady);
            user.setPassword(CommonHelper.makeMD5(visitingEmployee.getNo()));
            user.setUsername(visitingEmployee.getNo());
            visitingEmployeeReady.setUser(user);
            //保存user并级联保存employeeReady
            userService.save(user);
            //重新获取保存的user对象
            user = userService.uniqueResult("username", visitingEmployee.getNo());
            UserRole userRole = new UserRole();
            userRole.setRole(roleService.findById(10));
            userRole.setUser(user);
            userRoleService.save(userRole);
            //重新获取保存对象，visitingEmployee否则更新visitingEmployee会出错
            visitingEmployeeReady = visitingEmployeeService.uniqueResult("no", visitingEmployee.getNo());
        } else {
            visitingEmployeeReady = visitingEmployeeService.findById(visitingEmployee.getId());

            visitingEmployeeReady.setName(visitingEmployee.getName());
            visitingEmployeeReady.setNo(visitingEmployee.getNo());
            visitingEmployeeReady.setSex(visitingEmployee.getSex());
            visitingEmployeeReady.setCompany(visitingEmployee.getCompany());
            visitingEmployeeReady.setExperience(visitingEmployee.getExperience());
            visitingEmployeeReady.setForeignLanguage(visitingEmployee.getForeignLanguage());
            visitingEmployeeReady.setGraduateFrom(visitingEmployee.getGraduateFrom());
            visitingEmployeeReady.setPlan(visitingEmployee.getPlan());
            if (visitingEmployee.getProTitle() != null) {
                visitingEmployeeReady.setProTitle(proTitleService.uniqueResult("description", visitingEmployee.getProTitle().getDescription()));
            } else {
                visitingEmployeeReady.setProTitle(null);
            }
            if (visitingEmployee.getDegree() != null) {
                visitingEmployeeReady.setDegree(degreeService.uniqueResult("description", visitingEmployee.getDegree().getDescription()));
            } else {
                visitingEmployeeReady.setDegree(null);
            }
            if (visitingEmployeeReady.getContact() == null) {
                Contact contact = new Contact();
                contact.setEmail(visitingEmployee.getContact().getEmail());
                contact.setMoblie(visitingEmployee.getContact().getMoblie());
                contact.setQq(visitingEmployee.getContact().getQq());
                visitingEmployeeReady.setContact(contact);
            } else {
                visitingEmployeeReady.getContact().setEmail(visitingEmployee.getContact().getEmail());
                visitingEmployeeReady.getContact().setMoblie(visitingEmployee.getContact().getMoblie());
                visitingEmployeeReady.getContact().setQq(visitingEmployee.getContact().getQq());
            }
        }
        visitingEmployeeReady.setDepartment(departmentService.findById(departmentId));
        visitingEmployeeService.saveOrUpdate(visitingEmployeeReady);
    }

    //删除方法
    @RequestMapping("/visitingEmployeeDelete.html")
    public void delete(HttpServletResponse response, Integer visitingEmployeeId) {
        visitingEmployeeService.delete(visitingEmployeeService.findById(visitingEmployeeId));
        CommonHelper.buildSimpleJson(response);
    }

    //重置密码
    @RequestMapping("/resetVisitingEmployeePassword")
    public void resetPassword(HttpServletResponse response, Integer visitingEmployeeId) {
        System.out.println("-------");
        VisitingEmployee visitingEmployee = visitingEmployeeService.findById(visitingEmployeeId);
        User user = visitingEmployee.getUser();
        user.setPassword(CommonHelper.makeMD5(visitingEmployee.getNo()));
        userService.update(user);
        //对更新状态进行保存
        userService.save(user);
        CommonHelper.buildSimpleJson(response);
    }

}
