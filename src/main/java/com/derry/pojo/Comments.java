package com.derry.pojo;

//评论表
public class Comments {
    //评论主键
    private Integer id;
    //用户id,在此做外键
    private Integer userId;
    //商品id,在此做外键
    private Integer goodsId;
    //评论时间
    private String createAt;
    //评论内容
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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

    @Override
    public String toString() {
        return "Comments[" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", createAt='" + createAt + '\'' +
                ", content='" + content + '\'' +
                ']';
    }
}