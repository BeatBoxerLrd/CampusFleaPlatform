package com.derry.service.impl;

import com.derry.dao.GoodImageMapper;
import com.derry.pojo.GoodImage;
import com.derry.pojo.Goods;
import com.derry.service.GoodImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 18:16 2019/4/23
 * @Modified By:
 */
@Service("goodImageService")
public class GoodImageServiceImpl implements GoodImageService {
    @Resource
    private GoodImageMapper goodImageMapper;
    @Override
    public Map<String,Object> getAllGoodsCatelogByDate(Integer catelogId, int pageNum, int pageSize) {
        Map<String,Object> resMap = new HashMap<>();
        try{ PageHelper.startPage(pageNum,pageSize);
            List<GoodImage> goodsList = goodImageMapper.selectAllGoodsByCalteLogByDate(catelogId);
            //获取分页信息对象
            PageInfo<GoodImage> pageInfo = new PageInfo<>(goodsList);
            String total = String.valueOf(pageInfo.getTotal());
            String pages =  String.valueOf(pageInfo.getPages());
            resMap.put("data",goodsList);
            resMap.put("total",total);
            resMap.put("pages",pages);
            resMap.put("code",1);
            System.out.println("总记录数" + pageInfo.getTotal());//总记录数
            System.out.println("总页数" + pageInfo.getPages());//总页数
        }catch (Exception e){
            resMap.put("code",0);
        }
        return resMap;
    }
    @Override
    public Map<String,Object>  getAllGoodsByDate(int catelog, int pageNum, int pageSize) {
        Map<String,Object> resMap = new HashMap<>();
        try{
            PageHelper.startPage(pageNum,pageSize);
            List<GoodImage> goodsList = goodImageMapper.selectAllGoodsBydata();
            PageInfo<GoodImage> pageInfo = new PageInfo<>(goodsList);
            String total = String.valueOf(pageInfo.getTotal());
            String pages =  String.valueOf(pageInfo.getPages());
            resMap.put("data",goodsList);
            resMap.put("total",total);
            resMap.put("pages",pages);
            resMap.put("code",1);
        }catch (Exception e){
            resMap.put("code",0);
        }
        return resMap;
    }
}
