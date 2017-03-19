package com.newview.bysj.web.userManage.student;

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
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.StudentClass;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.exception.DatabaseException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.other.Common;
import com.newview.bysj.web.baseController.BaseController;
import com.newview.bysj.xls.SheetDb;

@Controller
public class StudentAdminStudentImportController extends BaseController {
    //上传学生文件
    @RequestMapping("/upLoadStudent.html")
    public String importStudent(HttpSession httpSession, ModelMap modelMap) {
        return "usersManage/student/toImportStudent";
    }

    @RequestMapping(value = "/importStudentsFromExcel.html", method = RequestMethod.POST)
    public String uploadStudent(MultipartFile file, HttpSession httpSession, ModelMap modelMap) throws Exception {
        //获取文件的根目录
        String fileName = this.getFileName(httpSession);
        try {
            if (!file.isEmpty()) {
                //转存文件
                file.transferTo(new File(fileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.addStudentsFormExcel(httpSession, modelMap);
        //System.out.println("导入成功");
        return "redirect:usersManage/student.html";
    }

    public void addStudentsFormExcel(HttpSession httpSession, ModelMap modelMap) throws Exception {
        long beginTime = Calendar.getInstance().getTimeInMillis();
        //设置完整的文件名
        String fileName = getFileName(httpSession);
        //根据文件创建工作表
        SheetDb sheetDb = new SheetDb(new File(fileName));
        //创建studentClassMap 班级名称-》班级对象
        Map<String, StudentClass> studentClassMap = getStudentClassMap();
        Student student = null;
        //显示每行记录的导入情况
        StringBuilder importReport = new StringBuilder();
        //本次导入增加的教师数
        int addCount = 0;
        //本次导入更新的教师数
        int updateCount = 0;

        UserRole userRole = null;
        Role role = null;
        //遍历工作表
        while (sheetDb.next()) {
            //获得编号列当前单元格的值
            String no = sheetDb.getCell("学号");
            if (!CommonHelper.isValidString(no)) {
                importReport = importReport.append("<font color=\"red\">失败:学号无效</font>").append("<br>");
            }
            //本行导入结果的占位符，每行导入结束后会被“成功”或“失败”代替
            importReport.append(Common.IMPORT_CONCLUSION_PLACEHOLDER + ":");
            //默认传入的学生不存在
            boolean newStudent = true;
            //根据学号查找相应的学生
            student = studentService.uniqueResult("no", no);
            //如果查到，则认为是根据excel内容更新数据库信息
            if (student != null) {
                newStudent = false;
            }
            User user = null;
            User hasSavedUser = null;
            //如果这个学生不存在
            if (newStudent) {
                //报告中增加导入教师信息
                importReport.append("导入学生，" + no + ",");
                //创建新的教师对象
                student = new Student();
                //给学生添加Contant对象
                student.setContact(new Contact());
                //给no赋值
                student.setNo(no);
                //从sheetDb中取出当前行数据并为employee的其他属性赋值
                boolean validRow = this.assignStudent(sheetDb, studentClassMap, student, importReport);
                //如果不是有效数据则该行不能导入
                if (!validRow) {
                    continue;
                } else {

                    user = new User();
                    user.setActor(student);
                    student.setUser(user);
                    user.setUsername(student.getNo());
                    user.setPassword(CommonHelper.makeMD5(student.getNo()));
                    /*//保存student对象,会为student关联“班级”
					studentService.save(student);*/
                    userService.save(user);
                    hasSavedUser = userService.uniqueResult("username", user.getUsername());
                    userRole = new UserRole();
                    role = roleService.findById(6);
                    userRole.setRole(role);
                    userRole.setUser(hasSavedUser);
                    userRoleService.save(userRole);
                    addCount++;
                }
            } else {
                studentService.update(student);
                //对更新状态进行保存
                studentService.save(student);
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

    private boolean assignStudent(SheetDb sheetDb, Map<String, StudentClass> studentClassMap, Student student,
                                  StringBuilder importReport) throws DatabaseException {
        //本行是否可以导入，默认为true
        boolean isAcceptRow = true;
        //从excel中获得“姓名”列的值
        String name = sheetDb.getCell("姓名");
        //如姓名有效
        if (CommonHelper.isValidString(name)) {
            student.setName(name);
        } else {
            importReport.append("姓名无效");
            //本行不可导入
            isAcceptRow = false;
        }
        String studentClassDescription = sheetDb.getCell("班级");
        StudentClass studentClass = studentClassMap.get(studentClassDescription);
        if (studentClass != null) {
            //为学生设置班级
            student.setStudentClass(studentClass);
        } else {
            importReport.append("所属班级无效");
            //本行不可导入
            isAcceptRow = false;
        }
        //从excel中获得性别的值
        String sex = sheetDb.getCell("性别");
        if ("男".equals(sex) || "女".equals(sex)) {
            //设置性别
            student.setSex(sex);
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

    public Map<String, StudentClass> getStudentClassMap() {
        List<StudentClass> studentClasses = studentClassService.findAll();
        Map<String, StudentClass> studentClassMap = new HashMap<String, StudentClass>();
        for (StudentClass studentClass : studentClasses) {
            studentClassMap.put(studentClass.getDescription(), studentClass);
        }
        return studentClassMap;
    }

    public String getFileName(HttpSession httpSession) {
        String root = CommonHelper.getRootPath(httpSession);
        return root + "/WEB-INF/studentList";
    }

}
