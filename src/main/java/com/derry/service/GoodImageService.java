package com.derry.service;

import com.derry.pojo.GoodImage;

import java.util.List;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 18:16 2019/4/23
 * @Modified By:
 */
public interface GoodImageService {
    /**
     * @Author：LiuRuidong
     * @Description: 获取每个分类商品并通过时间进行排序
     * @Date:2019/4/23 16:02
     * @Param:
     * @return
     */
    Map<String,Object> getAllGoodsCatelogByDate(Integer catelogId, int pageNum, int pageSize);

    /**
     * @Author：LiuRuidong
     * @Description:获取最新发布的商品
     * @Date:2019/4/30 19:49
     * @Param:
     * @return
     */
    Map<String,Object> getAllGoodsByDate(int catelog,int pageNum, int pageSize);
}
