package com.newview.bysj.initialize.domain;

import java.io.Serializable;

public class Role2Resource implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer resource;
    private Integer role;

    public Role2Resource() {
        super();
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


}
