package com.newview.bysj.web.schoolExcellentProject;

import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zhan on 2016/5/5.
 */
public class GraduateProjectListExcelDocument extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //获取所在的教研室
        Department department = (Department) map.get("department");
        //设置文件名
        String fileName = "山东建筑大学本科毕业设计明细表-" + department.getDescription() + ".xls";
        //设置头文件
        httpServletResponse.setHeader("Content-Disposition", "inline;filename=" + new String(fileName.getBytes(), "iso8859-1"));
        //新建一个页面
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("projectList");
        HSSFRow hssfRow = hssfSheet.createRow(0);
        int headerRowNum = 0;
        hssfSheet.setColumnWidth(headerRowNum, 1000);
        //设置表头元素
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "序号", 3500);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "学号", 3500);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "姓名", 2500);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "专业", 3000);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "班级", 2500);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "成绩", 1500);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "毕业设计(论文)题目", 12000, HSSFCellStyle.ALIGN_LEFT);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "类别", 1500);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "题目类型", 3000);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "题目性质", 3000);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "题目来源", 3000);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "教师姓名", 3000);
        setHeaderRow(hssfWorkbook, hssfSheet, hssfRow, headerRowNum++, "职称/学位", 3500);
        //获取要输出的数据
        List<GraduateProject> graduateProjectList = (List<GraduateProject>) map.get("projectList");
        //从第一行开始
        int rowNum = 1;
        for (GraduateProject graduateProject : graduateProjectList) {
            //创建行
            HSSFRow row = hssfSheet.createRow(rowNum++);
            //向单元格中插入数据
            Student student = graduateProject.getStudent();
            //从第一列开始
            int cellNum = 0;
            setCell(hssfWorkbook, row, cellNum++, rowNum - 1 + "");
            setCell(hssfWorkbook, row, cellNum++, rowNum - 1 + "");
            setCell(hssfWorkbook, row, cellNum++, student.getNo());
            if (student.getName() != null) {
                setCell(hssfWorkbook, row, cellNum++, student.getName());
            } else {
                setCell(hssfWorkbook, row, cellNum++, "无名氏");
            }

            setCell(hssfWorkbook, row, cellNum++, graduateProject.getMajor().getDescription());
            setCell(hssfWorkbook, row, cellNum++, student.getStudentClass().getDescription());
            if (graduateProject.getTotalScore() != null) {
                setCell(hssfWorkbook, row, cellNum++, graduateProject.getTotalScore().toString());
            } else {
                setCell(hssfWorkbook, row, cellNum++, "未打分");
            }
            setCell(hssfWorkbook, row, cellNum++, graduateProject.getTitle(), HSSFCellStyle.ALIGN_LEFT);
            if (graduateProject.getCategory() != null) {
                setCell(hssfWorkbook, row, cellNum++, graduateProject.getCategory());
            } else {
                setCell(hssfWorkbook, row, cellNum++, "未设置");
            }


            if (graduateProject.getProjectType() != null) {
                setCell(hssfWorkbook, row, cellNum++, graduateProject.getProjectType().getDescription());
            } else {
                setCell(hssfWorkbook, row, cellNum++, "未设置");
            }

            if (graduateProject.getProjectFrom() != null) {
                setCell(hssfWorkbook, row, cellNum++, graduateProject.getProjectFrom().getDescription());
            } else {
                setCell(hssfWorkbook, row, cellNum++, "未设置");
            }

            if (graduateProject.getProjectFidelity() != null) {
                setCell(hssfWorkbook, row, cellNum++, graduateProject.getProjectFidelity().getDescription());
            } else {
                setCell(hssfWorkbook, row, cellNum++, "未设置");
            }
            Tutor tutor = graduateProject.getMainTutorage().getTutor();
            setCell(hssfWorkbook, row, cellNum++, tutor.getName());
            if (tutor.getDegree() != null) {
                setCell(hssfWorkbook, row, cellNum++, tutor.getDegree().getDescription());
            }
        }
    }

    /**
     * 默认使用中间对齐
     *
     * @param row
     * @param cellNum
     * @param value
     * @return
     */
    private HSSFCell setCell(HSSFWorkbook workbook, HSSFRow row, int cellNum, String value) {
        return setCell(workbook, row, cellNum, value, HSSFCellStyle.ALIGN_CENTER);
    }

    /**
     * 生成单元格
     *
     * @param row
     * @param cellNum
     * @param value
     * @param align
     * @return
     */
    private HSSFCell setCell(HSSFWorkbook workbook, HSSFRow row, int cellNum, String value, short align) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(new HSSFRichTextString(value));
        //创建样式
        HSSFCellStyle style = workbook.createCellStyle();

        //设置字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);

        style.setFont(font);
        style.setAlignment(align);
        cell.setCellStyle(style);
        return cell;
    }

    private HSSFCell setHeaderRow(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row, int colNum, String value, int width) {
        sheet.setColumnWidth(colNum, width);
        return setCell(workbook, row, colNum, value);
    }

    private HSSFCell setHeaderRow(HSSFWorkbook workbook, HSSFSheet sheet, HSSFRow row, int colNum, String value, int width, short align) {
        sheet.setColumnWidth(colNum, width);
        return setCell(workbook, row, colNum, value, align);
    }
}
