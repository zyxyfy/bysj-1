package com.newview.bysj.web.usersManage;

public class QueryCondition {
    private Integer schoolId;
    private Integer departmentId;
    private Integer majorId;
    private String depscription;

    public QueryCondition() {
        super();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getDepscription() {
        return depscription;
    }

    public void setDepscription(String depscription) {
        this.depscription = depscription;
    }


}
