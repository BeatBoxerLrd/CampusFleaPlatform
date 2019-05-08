package com.derry.pojo;

import lombok.Data;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 17:43 2019/4/23
 * @Modified By:
 */
@Data
public class GoodImage {
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
    // 擦亮时间
    private String polishTime;
    //下架时间
    private String endTime;
    //详细信息
    private String describle;
    //评论回复数量
    private Integer commetNum;
    //商品图片
    private String imgUrl;
}
