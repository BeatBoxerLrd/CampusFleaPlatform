package com.derry.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 15:58 2019/4/29
 * @Modified By:
 */
public interface CodeService {
    Map<String ,Object>  identifyingCode(String phone, HttpServletRequest request);
}
