package com.newview.bysj.web.allocateProjectStudent;

import java.util.HashMap;

/**
 * 用来封装查询的条件
 * Created 2016/3/5,8:44.
 * Author 张战.
 */
public class QueryCondition {


    /**
     * 将查询的条件添加到map集合当中
     *
     * @param no   查询条件1
     * @param name 查询条件2
     * @return map集合
     */
    public HashMap<String, String> getQuery(String no, String name) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("no", no);
        hashMap.put("name", name);
        return hashMap;
    }

    /**
     * 将查询的条件添加到map集合当中，便于service层方法的调用
     *
     * @param title 查询条件1
     * @return map集合
     */
    public HashMap<String, String> projectQuery(String title) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("title", title);
        return queryMap;
    }


}
