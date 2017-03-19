package com.newview.bysj.other;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 当前页面的状态信息
 *
 * @author 张战
 */
public class PageCondition {

    /**
     * 当前页
     */
    private Integer pageNow;
    /**
     * 每页的条数
     */
    private Integer pageSize;
    /**
     * 需要排序的字段
     */
    private String orderField;
    /**
     * 排序的方向
     */
    private String orderDirector;

    /**
     * 无参的构造函数
     */
    public PageCondition() {
        super();
    }

    /**
     * 构造函数
     *
     * @param pageNow  当前页
     * @param pageSize 每页的条数
     */
    public PageCondition(Integer pageNow, Integer pageSize) {
        super();
        this.pageNow = pageNow;
        this.pageSize = pageSize;
    }

    /**
     * 构造函数
     *
     * @param pageNow       当前页
     * @param pageSize      每页的条数
     * @param orderField    需要排序的字段
     * @param orderDirector 排序的方向
     */
    public PageCondition(Integer pageNow, Integer pageSize, String orderField, String orderDirector) {
        super();
        this.pageNow = pageNow;
        this.pageSize = pageSize;
        this.orderField = orderField;
        this.orderDirector = orderDirector;
    }

    /**
     * 得到当前页
     *
     * @return 当前页
     */
    public Integer getPageNow() {
        return pageNow;
    }

    /**
     * 设置当前页
     *
     * @param pageNow 当前页
     */
    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    /**
     * @return 得到每页的条数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的条数
     *
     * @param pageSize 每页的条数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return 排序的字段
     */
    public String getOrderField() {
        return orderField;
    }

    /**
     * 设置排序的字段
     *
     * @param orderField 需要排序的字段
     */
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    /**
     * @return 得到排序的方向
     */
    public String getOrderDirector() {
        return orderDirector;
    }

    /**
     * 设置排序的方向，升序或降序
     *
     * @param orderDirector 排序的方向
     */
    public void setOrderDirector(String orderDirector) {
        this.orderDirector = orderDirector;
    }


    /**
     * @return 返回排序的方式，默认id排序
     */
    public Sort getSort() {
        if (Common.ASC.equals(orderDirector))
            return new Sort(Direction.ASC, orderField);
        else if (Common.DESC.equals(orderDirector))
            return new Sort(Direction.DESC, orderField);
        return new Sort(Direction.ASC, "id");
    }

    /**
     * @return 当前对象的字符串表示
     */
    @Override
    public String toString() {
        return "PageCondition [pageNum=" + pageNow + ", numPerPage=" + pageSize + ", orderField=" + orderField
                + ", orderDirector=" + orderDirector + "]";
    }

}
