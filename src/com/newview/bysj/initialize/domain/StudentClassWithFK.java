package com.newview.bysj.initialize.domain;

public class StudentClassWithFK {
    private String no;
    private String description;
    private Integer majorId;

    public StudentClassWithFK() {
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

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }


}
