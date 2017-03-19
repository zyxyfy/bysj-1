package com.newview.bysj.other;

import java.util.List;

/**
 * 分页相关
 *
 * @param <T>
 * @author 张战
 */
public class Paging<T> {

    /**
     * 每页默认的记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 默认当前页
     */
    public static final int DEFAULT_PAGE_NOW = 1;
    /**
     * 每页的记录数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 当前页
     */
    private int pageNow = DEFAULT_PAGE_NOW;
    /**
     * 数据的总行数
     */
    private Long totleRowCount;
    /**
     * 当前页中的数据
     */
    private List<T> data;

    public Paging() {
        super();
    }

    /**
     * @param pageSize      每页的记录数
     * @param pageNow       当前页
     * @param totleRowCount 数据的总条数
     */
    public Paging(int pageSize, int pageNow, Long totleRowCount) {
        super();
        this.pageSize = pageSize;
        this.pageNow = pageNow;
        this.totleRowCount = totleRowCount;
    }

    /**
     * @param pageSize      每页的记录数
     * @param pageNow       当前页
     * @param totleRowCount 数据的总条数
     * @param data          当前页中的数据
     */
    public Paging(int pageSize, int pageNow, Long totleRowCount, List<T> data) {
        super();
        this.pageSize = pageSize;
        this.pageNow = pageNow;
        this.totleRowCount = totleRowCount;
        this.data = data;
    }

    /**
     * 获取每页的条数
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的条数
     *
     * @param pageSize 每页的条数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 得到当前页
     *
     * @return
     */
    public int getPageNow() {
        return pageNow;
    }

    /**
     * 设置当前页
     *
     * @param pageNow 当前页
     */
    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    /**
     * 得到
     *
     * @return总条数
     */
    public Long getTotleRowCount() {
        return totleRowCount;
    }

    /**
     * 设置总条数
     *
     * @param totleRowCount 总条数
     */
    public void setTotleRowCount(Long totleRowCount) {
        this.totleRowCount = totleRowCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 得到总页数
     *
     * @return
     */
    public int getPageCount() {
        return (int) ((totleRowCount - 1) / pageSize + 1);
    }

    /**
     * 是否还有下一页
     *
     * @return 如果有下一页，则返回true
     */
    public boolean hasNextPage() {
        return pageNow < this.getPageCount();
    }

    /**
     * 是否还有上一页
     *
     * @return 如果有上一页，则返回true
     */
    public boolean hasPreviouPage() {
        return pageNow > 1;
    }

    /**
     * 获取任意一页中的第一条数据在总记录中的位置
     *
     * @param pageNow  当前页
     * @param pageSize 每页的条数
     * @return 页中的第一条数据在总记录中的位置
     */
    public static int getFirstResultInPage(int pageNow, int pageSize) {
        return (pageNow - 1) * pageSize;
    }

    /**
     * 通过默认的每页的记录数，来获取任意一页中的第一条数据在总记录中的条数
     *
     * @param pageNow 当前页
     * @return 当前页中的第一条数据在总记录中的条数
     */
    public static int getFirstResultInPageByDefault(int pageNow) {
        return (pageNow - 1) * DEFAULT_PAGE_SIZE;
    }
}
