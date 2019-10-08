package com.self.framework.ucenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.self.framework.annotation.NoSpecificationQuery;
import com.self.framework.base.BaseBean;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @des 用户实体
 */
@Entity(name = "t_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysUser extends BaseBean {

    /**
     *  用户id
     */
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "real_name")
    private String realName;//真实姓名

    @Column(name = "login_name", unique = true)
    private String loginName;//登录名

    @Column(name = "password")
    private String password;//密码

    @Column(name = "sex")
    private Integer sex;//性别

    @Column(name = "birthday")
    private String birthday;//生日

    @Column(name = "address")
    private String address;//详细地址

    @Column(name = "province")
    private String province;//详细地址

    @Column(name = "city")
    private String city;//城市

    @Column(name = "area")
    private String area;//区

    @Column(name = "status")
    private Integer status;//用户状态

    @Column(name = "type")
    private Integer type;//用户类型

    @Column(name = "is_delete")
    private Integer isDelete = 0;//是否被删除

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id" ) }) //被控方表字段名
    @NoSpecificationQuery
    private List<SysRole> userRoles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public List<SysRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<SysRole> userRoles) {
        this.userRoles = userRoles;
    }

    // public void setEncodePassword(String password) {
    //     PasswordEncoder encoder = new Md5PasswordEncoder();
    //     String encodePasswd = encoder.encode(password);
    //     this.password = encodePasswd;
    // }

}
