package com.derry.service.impl;

import com.derry.dao.GoodsMapper;
import com.derry.pojo.GoodImage;
import com.derry.pojo.Goods;
import com.derry.service.GoodsService;
import com.derry.util.DateUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 对商品的操作类（增删改查）
 * @ClassName 	GoodServiceImpl
 * @date		2017-5-9下午9:22:24
 */

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public int addGood(Goods goods , Integer duration) {
        String startTime = DateUtil.getNowDay();
        String endTime = DateUtil.getLastTime(startTime, duration);
        String polishTime = startTime;
        //添加上架时间，下架时间，擦亮时间
        goods.setPolishTime(polishTime);
        goods.setEndTime(endTime);
        goods.setStartTime(startTime);
        return goodsMapper.insert(goods);
    }


    @Override
    public Goods getGoodsByPrimaryKey(Integer goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        return goods;
    }

    @Override
    public void deleteGoodsByPrimaryKey(Integer id) {
        goodsMapper.deleteByPrimaryKey(id);
    }
    @Override
    public List<Goods> getAllGoods() {
        List<Goods> goods = goodsMapper.selectAllGoods();
        return goods;
    }
    @Override
    public List<Goods> searchGoods(String name, String describle) {
        List<Goods> goods = goodsMapper.searchGoods(name,describle);
        return  goods;
    }
    @Override
    public List<Goods> getGoodsByCatelog(Integer id,String name,String describle) {
        List<Goods> goods = goodsMapper.selectByCatelog(id,name,describle);
        return goods;
    }
    @Override
    public void updateGoodsByPrimaryKeyWithBLOBs(int goodsId,Goods goods) {
        goods.setId(goodsId);
        this.goodsMapper.updateByPrimaryKeyWithBLOBs(goods);
    }
    @Override
    public List<Goods> getGoodsByCatelogOrderByDate(Integer catelogId,Integer limit) {
        List<Goods> goodsList = goodsMapper.selectByCatelogOrderByDate(catelogId , limit);
        return goodsList;
    }
    @Override
    public List<Goods> getGoodsByUserId(Integer user_id) {
        List<Goods> goodsList = goodsMapper.getGoodsByUserId(user_id);
        return goodsList;
    }




}