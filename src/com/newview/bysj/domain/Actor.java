package com.newview.bysj.domain;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 所有用户的抽象父类，它的子类
 *
 * @see Student
 * @see Employee
 * @see VisitingEmployee
 * @see Tutor
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert(true)
@DynamicUpdate(true)
public abstract class Actor implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    /**
     * 教师的编号、学生的学号、校外指导教师的标号
     *
     * @generated
     */
    @Column(length = 12)
    private String no;

    @Column(length = 2)
    private String sex;

    @Column(length = 12)
    private String name;
    /**
     * 联系方式
     *
     * @generated
     */
    @JsonIgnore
    @Embedded
    private Contact contact;
    /**
     * 版本
     *
     * @generated
     */
    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * 一对一
     *
     * @generated
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 一对多
     * 发布的通知
     *
     * @generated
     */
    @JsonIgnore
    @OneToMany(mappedBy = "addressor", cascade = CascadeType.ALL)
    private List<Mail> mail;
    /**
     * 多对多
     * 收到的通知
     *
     * @generated
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.ALL)
    private List<Mail> receiveMail;

    public Actor() {
        super();
    }

    public Actor(String no, String name) {
        this.no = no;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonBackReference
    public List<Mail> getMail() {
        return mail;
    }

    @JsonBackReference
    public void setMail(List<Mail> mail) {
        this.mail = mail;
    }

    @JsonBackReference
    public List<Mail> getReceiveMail() {
        return receiveMail;
    }

    public void setReceiveMail(List<Mail> receiveMail) {
        this.receiveMail = receiveMail;
    }

	/*@Override
	public String toString() {
		return "Actor [id=" + id + ", no=" + no + ", sex=" + sex + ", name=" + name + ", contact=" + contact
				+ ", version=" + version + ", user=" + user + ", mail=" + mail + ", receiveMail=" + receiveMail + "]";
	}*/


}

