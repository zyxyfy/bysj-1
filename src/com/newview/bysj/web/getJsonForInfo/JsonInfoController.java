package com.newview.bysj.web.getJsonForInfo;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Major;
import com.newview.bysj.domain.StudentClass;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 2016/2/26,22:38.
 * Author 张战.
 */
@Controller
@RequestMapping("getJsonInfo")
public class JsonInfoController extends BaseController {

    private static final Logger logger = Logger.getLogger(JsonInfoController.class);


    @RequestMapping(value = "/getJsonMajorByDepartmentId.html", method = RequestMethod.POST)
    public void getJsonMajorByDepartment(Integer departmentId, HttpServletResponse httpServletResponse) {
        StringBuilder jsonBlock = new StringBuilder();
        jsonBlock.append("[");
        //如果点击教研室
        if (departmentId == 0) {
            this.noMajorInDepartment(jsonBlock, httpServletResponse);
            return;
        }
        //获取教研室下的所有专业
        List<Major> majorList = departmentService.findById(departmentId).getMajor();
        if (majorList == null) {
            this.noMajorInDepartment(jsonBlock, httpServletResponse);
            return;
        }
        for (Major major : majorList) {
            String entityJson = this.buildJsonByEntity(major.getId(), major.getDescription());
            jsonBlock.append(entityJson);
        }
        this.completeJson(jsonBlock, httpServletResponse);
    }

    //根据请求的学院的id,来获取该学院下所有的教研室
    //该方法给浏览器输出的json数据如以下格式[["1","管理工程"],["2","土木工程"]]
    @RequestMapping(value = "/getJsonDepartmentBySchoolId.html", method = RequestMethod.POST)
    public void getJsonDepartment(Integer schoolId, HttpServletResponse httpServletResponse) {
        //创建一个可变字符串序列，用以存储json数据块
        StringBuilder strBui = new StringBuilder();
        strBui.append("[");
        //如果传过来的学院为空，则教研室显示请选择教研室
        if (schoolId == 0) {
            this.noDepartmentInSchool(strBui, httpServletResponse);
            return;
        }
        //如果该学院下的教研室为空，则教研室显示请选择教研室
        if (schoolService.findById(schoolId).getDepartment().size() == 0) {
            this.noDepartmentInSchool(strBui, httpServletResponse);
            return;
        }
        //生成json的数据块
        for (Department department : schoolService.findById(schoolId).getDepartment()) {
            String entityJson = this.buildJsonByEntity(department.getId(), department.getDescription() + "教研室");
            //将数据添加到字符串序列中
            strBui.append(entityJson);
        }
        this.completeJson(strBui, httpServletResponse);
    }


    //根据请求的教研室的id,来获取该教研室下的所有班级，因为在一般的情况下，一个教研室只有一个专业，故没有必要通过教研室获取专业再获取班级
    @RequestMapping(value = "/getJsonStudentClassByDepartmentId.html", method = RequestMethod.POST)
    public void getJsonMajor(Integer departmentId, HttpServletResponse httpServletResponse) {
        StringBuilder jsonBlock = new StringBuilder();
        jsonBlock.append("[");
        //如果点击教研室
        if (departmentId == 0) {
            this.noStudentClassInDepartment(jsonBlock, httpServletResponse);
            return;
        }

        List<Major> majorList = departmentService.findById(departmentId).getMajor();
        List<StudentClass> studentClassList = new ArrayList<>();
        //获取教研室下的所有班级
        for (Major major : majorList) {
            studentClassList.addAll(majorService.findById(major.getId()).getStudentClass());
        }

        //如果教研室下没有班级
        if (studentClassList == null || studentClassList.size() == 0) {
            this.noStudentClassInDepartment(jsonBlock, httpServletResponse);
            return;
        }

        //不需要遍历教研室下的所有班级，直接遍历班级
       /* for (Major major : majorList) {
            String entityJson = this.buildJsonByEntity(major.getId(), major.getDescription());
            jsonBlock.append(entityJson);
        }*/
        for (StudentClass studentClass : studentClassList) {
            String entityJson = this.buildJsonByEntity(studentClass.getId(), studentClass.getDescription() + "班");
            jsonBlock.append(entityJson);
        }
        this.completeJson(jsonBlock, httpServletResponse);
    }


    /**
     * 根据专业的id来获取对应的班级
     * @param majorId 需要获取相应班级的id
     * @param httpServletResponse 给浏览器输出json数据
     */
    /*@RequestMapping(value = "/getJsonStudentClassByMajorId.html", method = RequestMethod.POST)
    public void getJsonStudentClass(Integer majorId, HttpServletResponse httpServletResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (majorId == null) {
            this.noMajorInDepartment(stringBuilder, httpServletResponse);
            return;
        }
        List<StudentClass> studentClassList = majorService.findById(majorId).getStudentClass();
        if (studentClassList == null || studentClassList.size() == 0) {
            this.noStudentClassInMajor(stringBuilder, httpServletResponse);
            return;
        }
        //遍历所有的班级
        for (StudentClass studentClass : studentClassList) {
            String jsonEntity = this.buildJsonByEntity(studentClass.getId(), studentClass.getDescription());
            stringBuilder.append(jsonEntity);
        }
        this.completeJson(stringBuilder, httpServletResponse);
    }*/


    /**
     * 把传进来的对象变成json格式,如["1","管理工程学院"]
     *
     * @param objects 参数可变
     * @return 方法的调用者
     */
    public String buildJsonByEntity(Object... objects) {
        StringBuilder entityJson = new StringBuilder();
        entityJson.append("[");
        for (Object object : objects) {
            String object1 = CommonHelper.putMarksObject(object);
            entityJson.append(object1);
            //不同的实体之间用逗号来隔开
            entityJson.append(",");
        }
        //删除最后一个逗号
        entityJson.deleteCharAt(entityJson.length() - 1);
        entityJson.append("]");
        entityJson.append(",");
        return entityJson.toString();
    }

    //学院下没有教研室
    public void noDepartmentInSchool(StringBuilder str, HttpServletResponse httpServletResponse) {
        str.append("[\"0\",\"没有教研室\"],");
        this.completeJson(str, httpServletResponse);
    }

    //教研室下没有专业
    public void noStudentClassInDepartment(StringBuilder stringBuilder, HttpServletResponse httpServletResponse) {
        stringBuilder.append("[\"0\",\"没有专业 \"],");
        this.completeJson(stringBuilder, httpServletResponse);
    }

    //专业下没有专业
    public void noMajorInDepartment(StringBuilder stringBuilder, HttpServletResponse httpServletResponse) {
        stringBuilder.append("[\"0\",\"没有专业 \"],");
        this.completeJson(stringBuilder, httpServletResponse);
    }

    //完善json块
    public void completeJson(StringBuilder str, HttpServletResponse httpServletResponse) {
        str.deleteCharAt(str.length() - 1);
        str.append("]");
        try {
            //设置类型
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().print(str.toString());
        } catch (IOException e) {
            throw new MessageException("获取信息失败！");
        }
    }
}
