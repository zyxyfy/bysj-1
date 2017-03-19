package com.newview.bysj.web.checkAcountComplete;

import java.text.NumberFormat;

/**
 * Created 2016/4/22,18:09.
 * Author 张战.
 */
public class DataBean {

    public DataBean() {
    }

    public DataBean(int shouldCompleteCount, int alreadyCompleteCount) {
        super();
        this.shouldCompleteCount = shouldCompleteCount;
        this.alreadyCompleteCount = alreadyCompleteCount;
    }

    //需要完成的数目
    private int shouldCompleteCount = 0;
    //已经完成的数目
    private int alreadyCompleteCount = 0;
    //已经完成的比例
    private String completionRate;

    public int getShouldCompleteCount() {
        return shouldCompleteCount;
    }

    public void setShouldCompleteCount(int shouldCompleteCount) {
        this.shouldCompleteCount = shouldCompleteCount;
    }

    public void setShouldCompleteCount() {
        this.shouldCompleteCount++;
    }

    public int getAlreadyCompleteCount() {
        return alreadyCompleteCount;
    }

    public void setAlreadyCompleteCount(int alreadyCompleteCount) {
        this.alreadyCompleteCount = alreadyCompleteCount;
    }

    public void setAlreadyCompleteCount() {
        this.alreadyCompleteCount++;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public void setCompletionRate() {

        if (alreadyCompleteCount != 0 || shouldCompleteCount != 0) {
            //getPercentInstance(),返回当前语言环境默认的百分比格式
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            //设置数的小数部分所允许的最小位数
            numberFormat.setMinimumFractionDigits(2);
            this.completionRate = numberFormat.format(1.00 * alreadyCompleteCount / shouldCompleteCount);
        } else {
            this.completionRate = "0.00%";
        }
    }
}
