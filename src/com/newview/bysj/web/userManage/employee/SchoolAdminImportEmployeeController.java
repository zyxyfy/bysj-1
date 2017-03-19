package com.newview.bysj.web.userManage.employee;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.newview.bysj.domain.Contact;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.exception.DatabaseException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.other.Common;
import com.newview.bysj.web.baseController.BaseController;
import com.newview.bysj.xls.SheetDb;

@Controller
public class SchoolAdminImportEmployeeController extends BaseController {
    //上传文件
    @RequestMapping(value = "/upLoadEmployee.html", method = RequestMethod.GET)
    public String importEmployee(ModelMap modelMap, HttpSession httpSession) {
        return "usersManage/employee/toImportEmployee";
    }

    @RequestMapping(value = "/importEmployeesFromExcel.html", method = RequestMethod.POST)
    public String upload(MultipartFile file, HttpSession httpSession, ModelMap modelMap) throws Exception {
        System.out.println("----------------------");
        //获取根目录下的文件名
        String fileName = this.getFileName(httpSession);
        System.out.println("after:fileName========" + fileName);
        if (!file.isEmpty()) {
            try {
                //转存文件
                file.transferTo(new File(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.addEmployeesFormExcel(httpSession, modelMap);
        //System.out.println("导入成功");
        return "redirect:usersManage/employee.html";
    }

    public String getFileName(HttpSession httpSession) {
        String root = CommonHelper.getRootPath(httpSession);
        //System.out.println("before:rootNama==========="+root);
        return root + "/WEB-INF/employeeNameList";
    }

    private void addEmployeesFormExcel(HttpSession httpSession, ModelMap modelMap) throws Exception {
        long beginTime = Calendar.getInstance().getTimeInMillis();
        //设置完整的文件名
        String fileName = getFileName(httpSession);
        //根据文件创建工作表
        SheetDb sheetDb = new SheetDb(new File(fileName));
        //创建departmentMap 教研室名称-》教研室对象
        Map<String, Department> departments = getDepartment();
        Employee employee = null;
        //显示每行记录的导入情况
        StringBuilder importReport = new StringBuilder();
        //本次导入增加的教师数
        int addCount = 0;
        //本次导入更新的教师数
        int updateCount = 0;
        //遍历工作表
        while (sheetDb.next()) {
            //获得编号列当前单元格的值
            String no = sheetDb.getCell("教师编号");
            if (!CommonHelper.isValidString(no)) {
                importReport = importReport.append("<font color=\"red\">失败:教师编号无效</font>").append("<br>");
            }
            //本行导入结果的占位符，每行导入结束后会被“成功”或“失败”代替
            importReport.append(Common.IMPORT_CONCLUSION_PLACEHOLDER + ":");
            //默认传入的老师不存在
            boolean newEmployee = true;
            //根据学号查找相应的教师
            employee = employeeService.uniqueResult("no", no);
            //如果查到，则认为是根据excel内容更新数据库信息
            if (employee != null) {
                newEmployee = false;
            }
            User user = null;
            //如果这个教师不存在
            if (newEmployee) {
                //报告中增加导入教师信息
                importReport.append("导入教师，" + no + ",");
                //创建新的教师对象
                employee = new Employee();
                //给教师添加Contant对象
                employee.setContact(new Contact());
                //给no赋值
                employee.setNo(no);
                //从sheetDb中取出当前行数据并为employee的其他属性赋值
                boolean validRow = this.assignEmployeee(sheetDb, departments, employee, importReport);
                //如果不是有效数据则该行不能导入
                if (!validRow) {
                    continue;
                } else {
                    user = new User();
                    user.setActor(employee);
                    employee.setUser(user);
                    user.setUsername(employee.getNo());
                    user.setPassword(CommonHelper.makeMD5(employee.getNo()));
                    /*//保存employee对象,会为employee关联“教研室”
					employeeService.save(employee);*/
                    userService.save(user);
                    //获取保存后的user
                    user = userService.uniqueResult("username", employee.getNo());
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(roleService.findById(10));
                    userRoleService.save(userRole);
                    addCount++;
                }
            } else {
                employeeService.update(employee);
                //对更新状态进行保存
                employeeService.save(employee);
                updateCount++;
            }
        }
        //得到导入结束时间
        long endTime = Calendar.getInstance().getTimeInMillis();
        importReport.insert(0, "<h3>用时：" + (endTime - beginTime)
                + "毫秒<br></h2>");
        importReport.insert(0, "<h3>更新：" + updateCount + "人<br></h2>");
        importReport.insert(0, "<h3>增加：" + addCount + "人<br></h2>");
        importReport.insert(0, "<h2>导入报告：<br></h2>");
    }

    private boolean assignEmployeee(SheetDb sheetDb, Map<String, Department> departmentMap, Employee employee,
                                    StringBuilder importReport) throws DatabaseException {
        //本行是否可以导入，默认为true
        boolean isAcceptRow = true;
        //从excel中获得“姓名”列的值
        String name = sheetDb.getCell("姓名");
        //如姓名有效
        if (CommonHelper.isValidString(name)) {
            employee.setName(name);
        } else {
            importReport.append("姓名无效");
            //本行不可导入
            isAcceptRow = false;
        }
        String departmentDescription = sheetDb.getCell("所属教研室");
        Department department = departmentMap.get(departmentDescription);
        if (department != null) {
            //为教师设置教研室
            employee.setDepartment(department);
        } else {
            importReport.append("所属教研室无效");
            //本行不可导入
            isAcceptRow = false;
        }
        //从excel中获得性别的值
        String sex = sheetDb.getCell("性别");
        if ("男".equals(sex) || "女".equals(sex)) {
            //设置性别
            employee.setSex(sex);
        } else {
            importReport.append("性别无效,");
        }
        // 占位符的开始位置
        int begin = importReport.indexOf(Common.IMPORT_CONCLUSION_PLACEHOLDER);
        System.out.println("begin======" + begin);
        // 将当前的占位符替换
        if (isAcceptRow) {
            // 成功
            importReport.replace(begin, begin + Common.IMPORT_CONCLUSION_PLACEHOLDER.length(), "成功");
        } else {
            // 失败
            importReport.replace(begin, begin + Common.IMPORT_CONCLUSION_PLACEHOLDER.length(),
                    "<font color=\"red\">失败</font>");
        }
        // 换行符
        importReport.append("<br>");
        // 返回本行是否导入
        return isAcceptRow;
    }

    public Map<String, Department> getDepartment() {
        List<Department> departments = departmentService.findAll();
        Map<String, Department> departmentMap = new HashMap<String, Department>();
        for (Department department : departments) {
            departmentMap.put(department.getDescription(), department);
        }
        return departmentMap;
    }

}
