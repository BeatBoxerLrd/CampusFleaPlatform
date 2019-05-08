package com.derry.pojo;

//回复表
public class Reply {
    private Integer id;
    //用户id
    private Integer userId;

    private Integer atuserId;
    //评论id
    private Integer commetId;
    //回复时间
    private String createAt;
    //回复内容
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAtuserId() {
        return atuserId;
    }

    public void setAtuserId(Integer atuserId) {
        this.atuserId = atuserId;
    }

    public Integer getCommetId() {
        return commetId;
    }

    public void setCommetId(Integer commetId) {
        this.commetId = commetId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}