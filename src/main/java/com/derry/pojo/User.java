package com.derry.pojo;

import java.io.Serializable;

//用户表
public class User implements Serializable {
    private Integer id;
    //用户手机号
    private String phone;
    //用户名
    private String username;
    //用户密码
    private String password;
    //用户qq号
    private String qq;
    //创建时间
    private String createAt;
    //发布过的商品数量
    private Integer goodsNum;
    //权限制，普通用户默认为10
    private Byte power;
    //最近一次登录时间
    private String lastLogin;
    //账户状态，是否被冻结，默认0未被冻结
    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Byte getPower() {
        return power;
    }

    public void setPower(Byte power) {
        this.power = power;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin == null ? null : lastLogin.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}