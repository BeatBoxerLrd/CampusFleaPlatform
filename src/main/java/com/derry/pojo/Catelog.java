package com.derry.pojo;


//商品分类表
public class Catelog {
    //主键
    private Integer id;
    //分类名
    private String name;
    //该分类下的商品数
    private Integer number;
    //分类状态，0正常，1暂用
    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Catelog[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", status=" + status +
                ']';
    }
}