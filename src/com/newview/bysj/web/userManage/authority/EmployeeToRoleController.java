package com.newview.bysj.web.userManage.authority;

import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeToRoleController extends BaseController {

    //private static final Logger logger = Logger.getLogger(EmployeeToRoleController.class);

    @RequestMapping("setEmployeeToRole")
    public String setEmployeeToRole(HttpSession httpSession, ModelMap modelMap, Integer employeeId) {
        Employee admin = employeeService.findById(CommonHelper.getCurrentActor(httpSession).getId());
        List<Role> roles = new ArrayList<Role>();
        for (UserRole userRole : admin.getUser().getUserRole()) {
            if (userRole.getRole().getRoleName().equals("ROLE_COLLEGE_ADMIN")) {
                roles.addAll(userRole.getRole().getRoleHandleds());
                // continue;
            } else if (userRole.getRole().getRoleName().equals("ROLE_SCHOOL_ADMIN")) {
                roles.addAll(userRole.getRole().getRoleHandleds());
                // continue;
            } else if (userRole.getRole().getRoleName().equals("ROLE_DEPARTMENT_DIRECTOR")) {
                roles.addAll(userRole.getRole().getRoleHandleds());
                // continue;
            }
        }
        Employee employee = employeeService.findById(employeeId);
        List<UserRole> userRoles = employee.getUser().getUserRole();
        List<Role> ownRoles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            for (Role role : roles) {
                if (userRole.getRole().getRoleHandler() != null && userRole.getRole().equals(role)) {
                    ownRoles.add(userRole.getRole());
                }
            }
        }
        roles.removeAll(ownRoles);
        modelMap.addAttribute("ownRoles", ownRoles);
        modelMap.addAttribute("noOwnRoles", roles);
        modelMap.addAttribute("employeeId", employeeId);
        return "usersManage/authority/userToRole";
    }

    /**
     * @param httpServletResponse 用于给浏览器输出json数据
     * @param selectRoleId        已拥有的角色的id
     * @param unselectedRoleId    未拥有的角色的id
     * @param employeeId          老师的id
     * @param model               用于存储需要在jsp中获取的数据
     * @return
     */
    @RequestMapping(value = "setEmployeeToRole.html", method = RequestMethod.POST)
    public void setEmployeeToRole(HttpServletResponse httpServletResponse, String selectRoleId, String unselectedRoleId, Integer employeeId, ModelMap model) {
        Employee employee = employeeService.findById(employeeId);
        Integer[] selectedList = selectRoleId.length() == 0 ? new Integer[0] : stringToInteger(selectRoleId);
        Integer[] unselectedList = unselectedRoleId.length() == 0 ? new Integer[0] : stringToInteger(unselectedRoleId);
        List<UserRole> userRoles = employee.getUser().getUserRole();
        // 如过selectedList中的角色在employee中的角色中已经存在，则需要消除重复
        for (UserRole userRole : userRoles) {
            for (int i = 0; i < selectedList.length; i++) {
                if (selectedList[i] == userRole.getRole().getId()) {
                    selectedList = arrayRemoveElement(selectedList, i);
                    break;
                }
            }
        }
        Role role;
        // 给用户添加角色
        for (int i = 0; i < selectedList.length; i++) {
            if (selectedList[i] == null) {
                continue;
            } else {
                role = roleService.findById(selectedList[i]);
                UserRole userRole = new UserRole();
                userRole.setUser(employee.getUser());
                userRole.setRole(role);
                userRoleService.save(userRole);
                employee.getUser().getUserRole().add(userRole);
            }
        }

        // 删除用户角色
        List<UserRole> toDeleteUserRoles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            for (int i = 0; i < unselectedList.length; i++) {
                if (unselectedList[i] == userRole.getRole().getId()) {
                    toDeleteUserRoles.add(userRole);
                }
            }
        }

        for (UserRole userRole : toDeleteUserRoles) {
            if (userRole == null) {
                continue;
            } else {
                userRole.getUser().getUserRole().remove(userRole);
                userRole.getRole().getUserRole().remove(userRole);
                userRole.setRole(null);
                userRole.setUser(null);
                userRoleService.saveOrUpdate(userRole);
                userRoleService.deleteObject(userRole);
            }
        }
        CommonHelper.buildSimpleJson(httpServletResponse, "修改成功");
    }

    public Integer[] arrayRemoveElement(Integer[] array, Integer elementIndex) {
        Integer[] newArray = new Integer[array.length - 1];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != elementIndex) {
                newArray[j] = array[i];
            } else {
                continue;
            }
        }
        return newArray;
    }

    public Integer[] stringToInteger(String str) {
        // 将字符串从第一个字符开始按，为分割标志截取成几个字符串
        String[] arrayList = str.substring(0).split(",");
        Integer[] array = new Integer[arrayList.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(arrayList[i]);
        }
        return array;

    }

}
