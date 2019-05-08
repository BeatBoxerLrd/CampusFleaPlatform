package com.derry.controller;

import com.derry.dto.BuyerCart;
import com.derry.dto.BuyerItem;
import com.derry.dto.Constants;
import com.derry.pojo.Goods;
import com.derry.pojo.User;
import com.derry.service.CartService;
import com.derry.util.RequestUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 15:37 2019/5/4
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/shopping")
public class CartController {
    @Autowired
    private CartService cartService;

    //amount默认为1
    @ResponseBody
    @RequestMapping(value="/buyerCart")
    public <T> String buyerCart(String itemId, String amount, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) throws JsonParseException, JsonMappingException, IOException {
        //将对象转换成json字符串/json字符串转成对象
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        BuyerCart buyerCart = null;
        //1,获取Cookie中的购物车
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("BUYER_CART".equals(cookie.getName())) {
                    //购物车 对象 与json字符串互转
                    buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
                    break;
                }
            }
        }

        //2,Cookie中没有购物车, 创建购物车对象
        if (null == buyerCart) {
            buyerCart = new BuyerCart();
        }

        //3, 将当前款商品追加到购物车
        if (null != itemId && null != amount) {
            BuyerItem item = new BuyerItem();
            item.setItemId(Integer.parseInt(itemId));
            item.setAmount(1);
            buyerCart.addItem(item);
        }

        //排序  倒序
        List<BuyerItem> items = buyerCart.getItems();
        Collections.sort(items, new Comparator<BuyerItem>() {
            @Override
            public int compare(BuyerItem o1, BuyerItem o2) {
                return -1;
            }

        });

        //前三点 登录和非登录做的是一样的操作, 在第四点需要判断

        //String username = RequestUtils.getAttributterForUsername(RequestUtils.getCSessionId(request, response));
        String username = (String) session.getAttribute("user");
        if (null != username) {
            //登录了
            //4, 将购物车追加到Redis中
            cartService.insertBuyerCartToRedis(buyerCart, username);
            //5, 清空Cookie 设置存活时间为0, 立马销毁
            Cookie cookie = new Cookie("BUYER_CART" , null);
            cookie.setPath("/");
            cookie.setMaxAge(-0);
            response.addCookie(cookie);
        }else {
            //未登录
            //4, 保存购物车到Cookie中
            //将对象转换成json格式
            Writer w = new StringWriter();
            om.writeValue(w, buyerCart);
            Cookie cookie = new Cookie("BUYER_CART", w.toString());
            //设置path是可以共享cookie
            cookie.setPath("/");
            //设置Cookie过期时间: -1 表示关闭浏览器失效  0: 立即失效  >0: 单位是秒, 多少秒后失效
            cookie.setMaxAge(24*60*60);
            //5,Cookie写会浏览器
            response.addCookie(cookie);
        }

        //6, 重定向
        return "redirect:/shopping/toCart";
    }

    //去购物车结算, 这里有两个地方可以直达: 1,在商品详情页 中点击加入购物车按钮  2, 直接点击购物车按钮
     @RequestMapping(value="/shopping/toCart")
     public String toCart(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
                 //将对象转换成json字符串/json字符串转成对象
         ObjectMapper om = new ObjectMapper();
         om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
         BuyerCart buyerCart = null;
         //1,获取Cookie中的购物车
         Cookie[] cookies = request.getCookies();
         if (null != cookies && cookies.length > 0) {
             for (Cookie cookie : cookies) {
                                 //
                 if (Constants.BUYER_CART.equals(cookie.getName())) {
                                         //购物车 对象 与json字符串互转
                     buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
                     break;
                 }
             }
         }

         //判断是否登录
         //String username = sessionProviderService.getAttributterForUsername(RequestUtils.getCSessionId(request, response));
         String username = (String) session.getAttribute("user");
         if (null != username) {
             //登录了
             //2, 购物车 有东西, 则将购物车的东西保存到Redis中
             if (null == buyerCart) {
                 cartService.insertBuyerCartToRedis(buyerCart, username);
                 //清空Cookie 设置存活时间为0, 立马销毁
                 Cookie cookie = new Cookie("BUYER_CART", null);
                 cookie.setPath("/");
                 cookie.setMaxAge(-0);
                 response.addCookie(cookie);
             }
             //3, 取出Redis中的购物车
             buyerCart = cartService.selectBuyerCartFromRedis(username);
         }


         //4, 没有 则创建购物车
         if (null == buyerCart) {
             buyerCart = new BuyerCart();
         }

                //5, 将购物车装满, 前面只是将skuId装进购物车, 这里还需要查出sku详情
              /*List<BuyerItem> items = buyerCart.getItems();
                if(items.size() > 0){
                        //只有购物车中有购物项, 才可以将sku相关信息加入到购物项中
                       for (BuyerItem buyerItem : items) {
                              //buyerItem.setSku(cartService.selectSkuById(buyerItem.getSku().getId()));
                            }
                    }*/

                 //5,上面已经将购物车装满了, 这里直接回显页面
               //  model.addAttribute("buyerCart", buyerCart);

              //跳转购物页面
         return "cart";
    }
}
