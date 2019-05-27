package com.derry.service.impl;


import com.derry.component.JedisClient;
import com.derry.dto.BuyerCart;
import com.derry.dto.BuyerItem;
import com.derry.pojo.Goods;
import com.derry.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.util.Set;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 15:53 2019/5/4
 * @Modified By:
 */
@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;

    @Override
    public void addCart(String itemId, String num, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Cookie[] cookies = request.getCookies();
    }

    @Override
    public List<Goods> getCartItems(HttpServletRequest request) {
        return null;
    }

    @Override
    public void updateCartItem(String itemId, String num, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

    }

    @Override
    public void deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {

    }

    //保存购物车到Redis中
     @Override
     public void insertBuyerCartToRedis(BuyerCart buyerCart, String username){
        List<BuyerItem> items = buyerCart.getItems();
        if (items.size() > 0) {
            //redis中保存的是skuId 为key , amount 为value的Map集合
            Map<String, String> hash = new HashMap<String, String>();
            for (BuyerItem item : items) {
                //判断是否有同款
                if (jedisClient.hexists("buyerCart:"+username, String.valueOf(item.getItemId()))) {
                    //jedisClient.hincrBy("buyerCart:"+username, String.valueOf(item.getSku().getId()), item.getAmount());
                }else {
                    hash.put(String.valueOf(item.getItemId()), String.valueOf(item.getAmount()));
                }
            }
            if (hash.size() > 0) {
                jedisClient.hmset("buyerCart:"+username, hash);
            }
        }

    }

    //取出Redis中购物车
    @Override
    public BuyerCart selectBuyerCartFromRedis(String username){
        BuyerCart buyerCart = new BuyerCart();
        //获取所有商品, redis中保存的是skuId 为key , amount 为value的Map集合
        Map<String, String> hgetAll = jedisClient.hgetAll("buyerCart:"+username);
        Set<Map.Entry<String, String>> entrySet = hgetAll.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                //entry.getKey(): skuId
                BuyerItem buyerItem = new BuyerItem();
                buyerItem.setItemId(Integer.parseInt(entry.getKey()));
                //entry.getValue(): amount
                buyerItem.setAmount(Integer.parseInt(entry.getValue()));
                //添加到购物车中
                buyerCart.addItem(buyerItem);
            }

            return buyerCart;
    }

    @Override
    public void delBuyerCart(String[] itemIds, String username) {
        if(itemIds.length!=0){
            for (int i = 0; i <itemIds.length ; i++) {
                jedisClient.hdel("buyerCart:"+username,itemIds[i]);
            }
        }
    }


}
