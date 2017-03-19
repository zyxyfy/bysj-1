package com.newview.bysj.initialize.domain;

import java.io.Serializable;

public class User2Role implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer user;
    private Integer role;

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


}
