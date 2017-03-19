package com.newview.bysj.initialize.domain;

public class MajorWithFK {
    private String no;
    private String description;
    private Integer departmentId;

    public MajorWithFK() {
        super();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }


}
