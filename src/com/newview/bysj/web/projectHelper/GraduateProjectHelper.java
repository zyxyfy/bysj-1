package com.newview.bysj.web.projectHelper;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.ProjectTimeSpan;
import com.newview.bysj.helper.CommonHelper;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.model.Model;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;

/**
 * Created 2016/3/6,19:35.
 * Author 张战.
 */
public class GraduateProjectHelper {

    public static final String VIEW_ALL = "all";

    public static final String VIEW_DESIGN = "design";

    public static final String VIEW_PAPAER = "paper";

    private static final Logger logger = Logger.getLogger(GraduateProjectHelper.class);

    public static String editProject(GraduateProject graduateProject) {
        if (graduateProject.getCategory().equals("设计题目"))
            return "redirect:/process/addOrEditDesignProject";
        else
            return "redirect:/process/addOrEditPaperProject";
    }

    /**
     * 用来在选题流程中，查看全部题目、查看论文题目、查看设计题目高亮显示当前的处于哪个功能下
     *
     * @param modelMap    用于存储需要在jsp中获取的数据
     * @param description 用于描述是论文还是设计
     */
    public static void viewDesignOrPaper(ModelMap modelMap, String description) {
        modelMap.put("viewProjectTitle", description);
    }

    /**
     * 查看细节，将graduateProject添加到map中，便于共用一个jsp
     *
     * @param modelMap        需要盛放graduateProject的集合
     * @param graduateProject 需要添加到集合中的数据
     */
    public static void viewProjectAddToModel(ModelMap modelMap, GraduateProject graduateProject) {
        modelMap.put("graduateProject", graduateProject);
    }

    //用来判断当前时间是否在毕业设置的时间之内
    public static void timeInProjectTimeSpan(ModelMap modelMap, ProjectTimeSpan projectTimeSpan) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String beginTime = simpleDateFormat.format(projectTimeSpan.getBeginTime().getTime());
        String endTime = simpleDateFormat.format(projectTimeSpan.getEndTime().getTime());
        String now = simpleDateFormat.format(CommonHelper.getNow().getTime());
        //两个日期型的字符串进行比较

        int compareTimeStart = CommonHelper.compareToString(beginTime, now);
        int compareTimeEnd = CommonHelper.compareToString(endTime, now);
        //毕业设计的开始时间在当前时间之前
        if (compareTimeStart < 0) {
            //毕业设计的结束时间在当前时间之后
            if (compareTimeEnd > 0) {
                modelMap.put("inTime", true);
            } else {
                modelMap.put("inTime", false);
                modelMap.addAttribute("beginTime", beginTime);
                modelMap.addAttribute("endTime", endTime);
            }
        } else {
            modelMap.put("inTime", false);
            modelMap.addAttribute("beginTime", beginTime);
            modelMap.addAttribute("endTime", endTime);
        }
    }
}
