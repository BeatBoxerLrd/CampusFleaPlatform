package com.derry.pojo;

//商品表
public class Goods {
    //评论主键
    private Integer id;
    //商品分类id，外键
    private Integer catelogId;
    //用户id,外键
    private Integer userId;
    //商品名称
    private String name;
    //出售价格
    private Float price;
    //真实价格
    private Float realPrice;
    //发布时间
    private String startTime;
    //下架时间
    private String endTime;
    // 擦亮时间
    private String polishTime;
    //评论回复数量
    private Integer commetNum;
    //详细信息
    private String describle;
    //商品状态
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatelogId() {
        return catelogId;
    }

    public void setCatelogId(Integer catelogId) {
        this.catelogId = catelogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Float realPrice) {
        this.realPrice = realPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getCommetNum() {
        return commetNum;
    }

    public void setCommetNum(Integer commetNum) {
        this.commetNum = commetNum;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle == null ? null : describle.trim();
    }

    public String getPolishTime() {
        return polishTime;
    }

    public void setPolishTime(String polishTime) {
        this.polishTime = polishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}