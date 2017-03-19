package com.newview.bysj.web.userManage.authority;

import com.newview.bysj.domain.Employee;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("usersManage")
public class EmployeeToRoleManageController extends BaseController {

    @RequestMapping("/employeeToRole")
    //判断用户的最高角色，进而获取不同的教师
    public String list(Integer pageNo, Integer pageSize, ModelMap modelMap) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        //根据用户的角色跳入不同的jsp中
        return "usersManage/authority/redirectEmployeeToRole";
    }

    //最高角色是教研室主任
    @RequestMapping("/department/employeeToRole")
    public String departmentEmployee(HttpSession httpSession, HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取当前用户所在教研室下的所有职工
        Page<Employee> employees = employeeService.getEmployeeByDepartment(employee.getDepartment().getId(), pageNo, pageSize);
        CommonHelper.paging(modelMap, employees, "employeeList");
        CommonHelper.pagingHelp(modelMap, employees, "employeeLiest", CommonHelper.getRequestUrl(httpServletRequest), employees.getTotalElements());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/department/searchEmployeeToRole.html");
        return "usersManage/authority/employeeToRole";
    }

    //最高角色是院级管理员
    @RequestMapping("/school/employeeToRole")
    public String schoolEmployee(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        //获取当前用户
        Employee employee = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取当前用户所在学院下的所有职工
        Page<Employee> employees = employeeService.getEmployeeBySchool(employee.getDepartment().getSchool().getId(), pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest), employees.getTotalElements());
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/school/searchEmployeeToRole.html");
        return "usersManage/authority/employeeToRole";
    }

    //最高角色是校级管理员
    @RequestMapping("/college/employeeToRole")
    public String collegeEmployee(HttpServletRequest httpServletRequest, ModelMap modelMap, Integer pageNo, Integer pageSize) {
        Page<Employee> employees = employeeService.getEmployeeByCollege(pageNo, pageSize);
        //CommonHelper.paging(modelMap,employees,"employeeList");
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest), employees.getTotalElements());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/college/searchEmployeeToRole.html");
        return "usersManage/authority/employeeToRole";
    }

    //教研室主任查询
    @RequestMapping(value = "/department/searchEmployeeToRole.html", method = RequestMethod.GET)
    public String searchEmployeeByDepartment(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, String name, String no, Integer pageNo, Integer pageSize) {
        //获取当前用户
        Employee employee = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        //获取该用户所在教研室的所有职工
        Integer departmentId = employee.getDepartment().getId();
        Integer schoolId = employee.getDepartment().getSchool().getId();
        Page<Employee> employees = searchEmployeeList(employee, name, no, departmentId, schoolId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", httpServletRequest.getRequestURI(), employees.getTotalElements());
        return "usersManage/authority/employeeToRole";
    }

    //院级管理员查询
    @RequestMapping(value = "/school/searchEmployeeToRole.html", method = RequestMethod.GET)
    public String searchEmployeeBySchool(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, String name, String no, Integer departmentSelect, Integer pageNo, Integer pageSize) {
        System.out.println("l;askdjflaidfaksdf:    " + departmentSelect);
        Employee employee = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        Integer schoolSelect = employee.getDepartment().getSchool().getId();
        Page<Employee> employees = searchEmployeeList(employee, name, no, departmentSelect, schoolSelect, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", httpServletRequest.getRequestURI(), employees.getTotalElements());
        modelMap.put("departmentSelect", departmentSelect);
        modelMap.put("name", name);
        modelMap.put("no", no);
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        return "usersManage/authority/employeeToRole";
    }

    //校级管理员查询
    @RequestMapping(value = "/college/searchEmployeeToRole.html", method = RequestMethod.GET)
    public String searchEmployeeByCollege(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap, String name, String no, Integer departmentSelect, Integer schoolSelect, Integer pageNo, Integer pageSize) {
        System.out.println("l;askdjflaidfaksdf:    " + departmentSelect);
        Employee employee = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        Page<Employee> employees = searchEmployeeList(employee, name, no, departmentSelect, schoolSelect, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest), employees.getTotalElements());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        modelMap.put("no", no);
        modelMap.put("name", name);
        modelMap.put("departmentSelect", departmentSelect);
        modelMap.put("schoolSelect", schoolSelect);
        return "usersManage/authority/employeeToRole";
    }

    public Page<Employee> searchEmployeeList(Employee employee, String employeeName, String employeeNo, Integer departmentId, Integer schoolId, Integer pageNo, Integer pageSize) {
        Page<Employee> employees;
        if (Objects.equals(employeeName, ""))
            employeeName = null;
        if (Objects.equals(employeeNo, ""))
            employeeNo = null;
        if (schoolId == null)
            schoolId = employee.getDepartment().getSchool().getId();
        if (departmentId == null)
            departmentId = employee.getDepartment().getId();
        if (schoolId == 0) {
            employees = employeeService.getEmployees(employeeNo, employeeName,
                    pageNo, pageSize);
        } else {
            if (departmentId == 0) {
                if (employeeNo == null && employeeName == null) {
                    employees = employeeService.getEmployeeBySchool(schoolId,
                            pageNo, pageSize);
                } else {
                    employees = employeeService.getEmployeeBySchool(employeeNo, employeeName, schoolId, pageNo, pageSize);
                }
            } else {
                if (employeeNo == null && employeeName == null) {
                    employees = employeeService.getEmployeeByDepartment(departmentId, pageNo, pageSize);
                } else {
                    employees = employeeService.getEmployeeByDepartment(employeeNo, employeeName, departmentId, pageNo, pageSize);
                }
            }
        }
        return employees;
    }


}
