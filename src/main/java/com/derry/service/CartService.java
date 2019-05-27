package com.derry.service;

import com.derry.dto.BuyerCart;
import com.derry.pojo.Goods;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author:LiuRuidong
 * @Description:购物和服务
 * @Date: Created in 15:48 2019/5/4
 * @Modified By:
 */
public interface CartService {
        void addCart(String itemId, String num, HttpServletRequest request, HttpServletResponse response, HttpSession session);

        List<Goods> getCartItems(HttpServletRequest request);

        void updateCartItem(String itemId, String num, HttpServletRequest request, HttpServletResponse response,HttpSession session);

        void  deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response);

        void insertBuyerCartToRedis(BuyerCart buyerCart, String username);

        BuyerCart selectBuyerCartFromRedis(String username);

        void delBuyerCart(String[] itemIds,String username);
}
