package com.derry.dto;

import com.derry.pojo.Goods;
import com.derry.service.GoodsService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 0:11 2019/5/5
 * @Modified By:
 */
@Component
public class BuyerCart implements Serializable {
    @Autowired
    private GoodsService goodsService;

    private static final long serialVersionUID = 1L;

    private List<BuyerItem> items = new ArrayList<BuyerItem>();

    //添加购物项到购物车
    public void addItem(BuyerItem item){
        //判断是否包含同款
        if (items.contains(item)) {
            //追加数量
            for (BuyerItem buyerItem : items) {
                if (buyerItem.equals(item)) {
                    //对于该二手平台什么都不需要操作
                    //buyerItem.setAmount(item.getAmount() + buyerItem.getAmount());
                    buyerItem.setAmount(1);
                }
            }
        }else {
            items.add(item);
        }

    }

    public List<BuyerItem> getItems() {
        return items;
    }

    public void setItems(List<BuyerItem> items) {
        this.items = items;
    }

    //小计
    //商品数量
    @JsonIgnore
    public Integer getProductAmount(){
        Integer result = 0;
        //计算
        for (BuyerItem buyerItem : items) {
            result += 1;
        }
        return result;
    }

    //商品金额
    @JsonIgnore
    public Float getProductPrice(){
        Float result = 0f;
        //计算
        for (BuyerItem buyerItem : items) {
            result += 1*goodsService.getGoodsByPrimaryKey(buyerItem.getItemId()).getPrice();
        }
        return result;
    }

    //运费
    @JsonIgnore
    public Float getFee(){
        Float result = 0f;
        //计算
        if (getProductPrice() < 79) {
            result = 5f;
        }
        return result;
    }
    //总价
    @JsonIgnore
    public Float getTotalPrice(){
        return getProductPrice() + getFee();
    }

    @Override
    public String toString() {
        return "BuyerCart{" +
                "goodsService=" + goodsService +
                ", items=" + items +
                '}';
    }
}
