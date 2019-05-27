package com.derry.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 12:19 2019/5/8
 * @Modified By:
 */
public class Test {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("code",1);
        System.out.println(map.get("code").equals(1));
    }
}
