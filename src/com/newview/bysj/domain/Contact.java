package com.newview.bysj.domain;

import javax.persistence.Embeddable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Embeddable
@DynamicInsert(true)
@DynamicUpdate(true)
public class Contact {
    /**
     * @generated
     */
    private String qq;
    /**
     * @generated
     */
    private String moblie;
    /**
     * @generated
     */
    private String zipCode;
    /**
     * @generated
     */
    private String email;

    public Contact() {
        super();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
