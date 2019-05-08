package com.derry.dao;

import com.derry.pojo.GoodImage;
import com.derry.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 18:13 2019/4/23
 * @Modified By:
 */
public interface GoodImageMapper {
    List<GoodImage> selectAllGoodsByCalteLogByDate(@Param("catelogId") Integer catelogId);

    /**
     * @Author：LiuRuidong
     * @Description: 查询所有商品通过时间显示
     * @Date:2019/4/23 16:00
     * @Param:
     * @return
     */
    List<GoodImage> selectAllGoodsBydata();

}
