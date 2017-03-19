package com.newview.bysj.other;

/**
 * 项目中需要使用的一些常量
 *
 * @author zhan
 */
public class Common {

    /**
     * 升序
     */
    public final static String DESC = "desc";
    /**
     * 降序
     */
    public final static String ASC = "asc";
    /**
     * 每页的条数
     */
    public final static Integer NUM_PER_PAGE = 20;
    /**
     * 文件上传的目录
     */
    public final static String UPDATE_DIR = "//update//";
    /**
     * 常量值在自定义标签类中使用
     */
    public final static String DISPLAY_PATTERN = "displayPattern";
    /**
     * 从excel导入记录时，每行会生成一个导入结论，此占位符代表本行导入结论，每行导入后会被“成功”或“失败”代替
     */
    public final static String IMPORT_CONCLUSION_PLACEHOLDER = "*RowConclusion*";
}
