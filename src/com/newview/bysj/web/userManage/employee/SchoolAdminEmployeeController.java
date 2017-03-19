package com.newview.bysj.web.userManage.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

@Controller
@RequestMapping("usersManage")
public class SchoolAdminEmployeeController extends BaseController {
    private static final Logger logger = Logger.getLogger(SchoolAdminEmployeeController.class);

    // 判断当前用户的最高角色，进入不同的jsp页面
    @RequestMapping("/employee")
    public String list(ModelMap modelMap, Integer pageNo, Integer pageSize) {
        modelMap.put("pageNo", pageNo);
        modelMap.put("pageSize", pageSize);
        // 根据用户的角色不同跳入到不同的JSP中
        return "usersManage/employee/redirectEmployeeManage";
    }

    // 最高角色教研室主任
    @RequestMapping("/department/employee")
    public String departmentEmployee(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap,
                                     Integer pageNo, Integer pageSize) {
        // 获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        // 获取该用户教研室下的所有职工
        Page<Employee> employees = employeeService.getEmployeeByDepartment(employee.getDepartment().getId(), pageNo,
                pageSize);
        // 分页
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest),
                employees.getTotalElements());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/department/searchEmployee.html");
        modelMap.addAttribute("edit", "department");
        return "usersManage/employee/schoolAdminEmployeeList";

    }

    // 最高角色是院级管理员
    @RequestMapping("/school/employee")
    public String schoolEmployee(HttpServletRequest httpServletRequest, HttpSession httpSession, ModelMap modelMap,
                                 Integer pageNo, Integer pageSize) {
        // 获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        // 获取该用户学院下的职工
        Page<Employee> employees = employeeService.getEmployeeBySchool(employee.getDepartment().getSchool().getId(),
                pageNo, pageSize);
        // 分页
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest),
                employees.getTotalElements());
        modelMap.addAttribute("departments", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/school/searchEmployee.html");
        modelMap.addAttribute("edit", "school");
        return "usersManage/employee/schoolAdminEmployeeList";
    }

    // 最高角色是校级管理员
    @RequestMapping("/college/employee")
    public String collegeEmployee(HttpServletRequest req, HttpSession httpSession, ModelMap modelMap, Integer pageNo,
                                  Integer pageSize) {
        Page<Employee> employees = employeeService.getEmployeeByCollege(pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", req.getRequestURI(), employees.getTotalElements());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        modelMap.addAttribute("actionUrl", "/bysj3/usersManage/college/searchEmployee.html");
        modelMap.addAttribute("edit", "college");
        return "usersManage/employee/schoolAdminEmployeeList";
    }

    // 教研室主任查询
    @RequestMapping(value = "/department/searchEmployee.html", method = RequestMethod.GET)
    public String searEmployeeByDepartment(HttpSession httpSession, ModelMap modelMap, String no, String name,
                                           Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        // 获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        // 根据教研室获得职工
        Integer departmentId = employee.getDepartment().getId();
        Integer schoolId = employee.getDepartment().getSchool().getId();
        Page<Employee> employees = searchEmployeeList(employee, name, no, departmentId, schoolId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest),
                employees.getTotalElements());
        //添加检索时所需要的属性
        modelMap.put("no", no);
        modelMap.put("name", name);
        return "usersManage/employee/schoolAdminEmployeeList";
    }

    // 院级管理员查询
    @RequestMapping(value = "/school/searchEmployee.html", method = RequestMethod.GET)
    public String searchEmployeeBySchool(HttpSession httpSession, ModelMap modelMap, String no, String name,
                                         Integer departmentId, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        // 获取当前用户
        Employee employee = employeeService.findById(((Employee) CommonHelper.getCurrentActor(httpSession)).getId());
        Integer schoolId = employee.getDepartment().getSchool().getId();
        // 获取用户所在学院下的职工
        Page<Employee> employees = searchEmployeeList(employee, name, no, departmentId, schoolId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest),
                employees.getTotalElements());
        modelMap.addAttribute("departments", employee.getDepartment().getSchool().getDepartment());
        //添加查询所需要的属性
        modelMap.put("no", no);
        modelMap.put("name", name);
        modelMap.put("departmentId", departmentId);
        return "usersManage/employee/schoolAdminEmployeeList";

    }

    // 校级管理员查询
    @RequestMapping(value = "/college/searchEmployee.html", method = RequestMethod.GET)
    public String searchEmployeeByCollege(ModelMap modelMap, HttpSession httpSession,
                                          HttpServletRequest httpServletRequest, String no, String name, Integer departmentId, Integer schoolId,
                                          Integer pageNo, Integer pageSize) {
        // 获取当前用户
        Employee employee = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        // 获取当前用户所在学校的职工
        Page<Employee> employees = searchEmployeeList(employee, name, no, departmentId, schoolId, pageNo, pageSize);
        CommonHelper.pagingHelp(modelMap, employees, "employeeList", CommonHelper.getRequestUrl(httpServletRequest),
                employees.getTotalElements());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        //添加检索时所需要的属性
        modelMap.put("no", no);
        modelMap.put("name", name);
        modelMap.put("departmentId", departmentId);
        modelMap.put("schoolId", schoolId);
        return "usersManage/employee/schoolAdminEmployeeList";

    }

    // 教研室主任添加
    @RequestMapping(value = "/department/employeeAdd.html", method = RequestMethod.GET)
    public String departmentAddEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        paperModel(request, modelMap);
        return "usersManage/employee/addOrEditEmployee";
    }

    // 院级管理员添加
    @RequestMapping(value = "/school/employeeAdd.html", method = RequestMethod.GET)
    public String schoolAddEmployee(HttpServletRequest request, HttpSession httpSession, ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        paperModel(request, modelMap);
        return "usersManage/employee/addOrEditEmployee";
    }

    // 校级管理员添加
    @RequestMapping(value = "/college/employeeAdd.html", method = RequestMethod.GET)
    public String collegeAddEmployee(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        paperModel(httpServletRequest, modelMap);
        return "usersManage/employee/addOrEditEmployee";
    }

    @RequestMapping(value = "/department/employeeAdd.html", method = RequestMethod.POST)
    public String departmentAddEmployee(@ModelAttribute("employee") Employee employee,
                                        HttpServletResponse httpServletResponse, Integer departmentId) {
        addOrEditEmployee(employee, departmentId);
        return "redirect:/usersManage/employee.html";
    }

    @RequestMapping(value = "/school/employeeAdd.html", method = RequestMethod.POST)
    public String schoolAddEmployee(HttpServletResponse httpServletResponse,
                                    @ModelAttribute("employee") Employee employee, Integer departmentId) {
        addOrEditEmployee(employee, departmentId);
        return "redirect:/usersManage/employee.html";
    }

    @RequestMapping(value = "/college/employeeAdd.html", method = RequestMethod.POST)
    public String collegeAddEmployee(HttpServletResponse httpServletResponse,
                                     @ModelAttribute("employee") Employee employee, Integer departmentId) {
        addOrEditEmployee(employee, departmentId);
        return "redirect:/usersManage/employee.html";
    }

    // 教研室主任修改
    @RequestMapping("/department/employeeEdit.html")
    public String editEmployeeByDepartment(HttpSession httpSession, HttpServletRequest request, ModelMap modelMap,
                                           Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);
        modelMap.addAttribute("employee", employee);
        modelMap.addAttribute("currentDepartment", employee.getDepartment());
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("edited", true);
        paperModel(request, modelMap);
        return "usersManage/employee/addOrEditEmployee";

    }

    // 院级管理员修改
    @RequestMapping("/school/employeeEdit.html")
    public String editEmployeeBySchool(HttpSession httpSession, HttpServletRequest request, ModelMap modelMap,
                                       Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);
        modelMap.addAttribute("employee", employee);
        modelMap.addAttribute("currentDepartment", employee.getDepartment());
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("edited", true);
        paperModel(request, modelMap);
        return "usersManage/employee/addOrEditEmployee";
    }

    // 校级管理员修改
    @RequestMapping("/college/employeeEdit.html")
    public String editEmployeeByCollege(HttpSession httpSession, HttpServletRequest request, ModelMap modelMap,
                                        Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);
        modelMap.addAttribute("employee", employee);
        modelMap.addAttribute("currentDepartment", employee.getDepartment());
        modelMap.addAttribute("departmentList", employee.getDepartment().getSchool().getDepartment());
        modelMap.addAttribute("edited", true);
        paperModel(request, modelMap);
        return "usersManage/employee/addOrEditEmployee";
    }

    @RequestMapping(value = "/department/employeeEdit.html", method = RequestMethod.POST)
    public String editEmployeeByDeparmtment(HttpServletResponse httpServletResponse,
                                            HttpServletRequest httpServletRequest, @ModelAttribute("employee") Employee employee,
                                            Integer departmentId) {
        addOrEditEmployee(employee, departmentId);
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/usersManage/employee.html";
    }

    @RequestMapping(value = "/school/employeeEdit.html", method = RequestMethod.POST)
    public String editEmployeeBySchool(HttpServletResponse httpServletResponse,
                                       @ModelAttribute("employee") Employee employee, Integer departmentId) {
        addOrEditEmployee(employee, departmentId);
        return "redirect:/usersManage/employee.html";
    }

    @RequestMapping(value = "/college/employeeEdit.html", method = RequestMethod.POST)
    public String editEmployeeByCollege(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest,
                                        @ModelAttribute("employee") Employee employee, Integer departmentId) {
        addOrEditEmployee(employee, departmentId);
        return "redirect:/usersManage/employee.html";
    }

    public void addOrEditEmployee(Employee employee, Integer departmentId) {
        Employee employeeReady = null;
        if (employee.getId() == null) {
            if (employee.getProTitle() != null) {
                employee.setProTitle(
                        proTitleService.uniqueResult("description", employee.getProTitle().getDescription()));
            } else {
                employee.setProTitle(null);
            }
            if (employee.getDegree() != null) {
                employee.setDegree(degreeService.uniqueResult("description", employee.getDegree().getDescription()));
            } else {
                employee.setDegree(null);
            }

            employeeReady = employee;
            // 为employee设置登陆用户
            User user = new User();
            user.setActor(employeeReady);
            user.setPassword(CommonHelper.makeMD5(employee.getNo()));
            user.setUsername(employee.getNo());
            employeeReady.setUser(user);
            // 保存user并级联保存employeeReady
            userService.save(user);
            // 重新获取保存的user对象
            user = userService.uniqueResult("username", employee.getNo());
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(roleService.findById(10));
            userRoleService.save(userRole);
            // 重新获取保存对象否则更新employee会出错
            employeeReady = employeeService.uniqueResult("no", employee.getNo());
        } else {
            employeeReady = employeeService.findById(employee.getId());
            employeeReady.setName(employee.getName());
            employeeReady.setNo(employee.getNo());
            employeeReady.setSex(employee.getSex());
            if (employee.getProTitle() != null) {
                employeeReady.setProTitle(
                        proTitleService.uniqueResult("description", employee.getProTitle().getDescription()));
            } else {
                employeeReady.setProTitle(null);
            }
            if (employee.getDegree() != null) {
                employeeReady
                        .setDegree(degreeService.uniqueResult("description", employee.getDegree().getDescription()));
            } else {
                employeeReady.setDegree(null);
            }
            if (employeeReady.getContact() == null) {
                Contact contact = new Contact();
                contact.setEmail(employee.getContact().getEmail());
                contact.setMoblie(employee.getContact().getMoblie());
                contact.setQq(employee.getContact().getQq());
                employeeReady.setContact(contact);
            } else {
                employeeReady.getContact().setEmail(employee.getContact().getEmail());
                employeeReady.getContact().setMoblie(employee.getContact().getMoblie());
                employeeReady.getContact().setQq(employee.getContact().getQq());
            }
        }
        employeeReady.setDepartment(departmentService.findById(departmentId));
        employeeService.saveOrUpdate(employeeReady);
    }

    public void paperModel(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("degree", degreeService.findAll());
        modelMap.addAttribute("proTitle", proTitleService.findAll());
        modelMap.addAttribute("schoolList", schoolService.findAll());
        modelMap.addAttribute("postActionUrl", CommonHelper.getRequestUrl(request));
    }

    public Page<Employee> searchEmployeeList(Employee employee, String employeeName, String employeeNo,
                                             Integer departmentId, Integer schoolId, Integer pageNo, Integer pageSize) {
        Page<Employee> employees = null;
        if (employeeNo == "") {
            employeeNo = null;
        }
        if (employeeName == "") {
            employeeName = null;
        }
        if (schoolId == null) {
            schoolId = employee.getDepartment().getSchool().getId();
        }
        if (departmentId == null) {
            departmentId = employee.getDepartment().getId();
        }
        if (schoolId == 0) {
            employees = employeeService.getEmployees(employeeNo, employeeName, pageNo, pageSize);
        } else {
            if (departmentId == 0) {
                if (employeeNo == null && employeeName == null) {
                    employees = employeeService.getEmployeeBySchool(schoolId, pageNo, pageSize);
                } else {
                    employees = employeeService.getEmployeeBySchool(employeeNo, employeeName, schoolId, pageNo,
                            pageSize);
                }
            } else {
                if (employeeNo == null && employeeName == null) {
                    employees = employeeService.getEmployeeByDepartment(departmentId, pageNo, pageSize);
                } else {
                    employees = employeeService.getEmployeeByDepartment(employeeNo, employeeName, departmentId, pageNo,
                            pageSize);
                }
            }
        }
        return employees;

    }

    // 删除
    @RequestMapping("/employeeDelete")
    public void deleteEmployee(HttpServletResponse httpServletResponse,
                               @ModelAttribute("employeeId") Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);
        employeeService.deleteEmployee(employee);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    // 重置密码
    @RequestMapping(value = "/resetpassWord")
    public void resetPassWord(HttpServletResponse httpServletResponse, Integer employeeId) {
        Employee employee = employeeService.findById(employeeId);
        User user = employee.getUser();
        user.setPassword(CommonHelper.makeMD5(employee.getNo()));
        userService.update(user);
        // 对更新状态进行保存
        userService.save(user);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
}
