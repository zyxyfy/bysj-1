package com.newview.bysj.web.checkTitle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 此类未发现有实际的作用，可能仅作测试使用
 * 当前关于这个类未作过多的说明，故没有轻易删除
 *
 * @author zhan
 */
@Controller
@RequestMapping("process")
public class StaticsJspController {


    /**
     * 测试使用
     *
     * @return jsp
     */
    @RequestMapping("/approveProjectsOfTutor.html")
    public String test() {
        return "projectTitle/checkTitle";
    }
}
