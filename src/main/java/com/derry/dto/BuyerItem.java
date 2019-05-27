package com.derry.dto;

import com.derry.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 14:38 2019/5/5
 * @Modified By:
 */
public class BuyerItem  implements Serializable {

    @Autowired
    private GoodsService goodsService;

    private static final long serialVersionUID = 1L;

    private Integer itemId;

    //是否有货
    private Boolean isHave = true;

    //购买的数量
    private Integer amount = 1;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Boolean getIsHave() {
        return isHave;
    }

    public void setIsHave(Boolean isHave) {
        this.isHave = isHave;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {

        return Objects.hash(goodsService, isHave, amount);
    }

    @Override
    public String toString() {
        return "BuyerItem{" +
                "goodsService=" + goodsService +
                ", itemId=" + itemId +
                ", isHave=" + isHave +
                ", amount=" + amount +
                '}';
    }
}

