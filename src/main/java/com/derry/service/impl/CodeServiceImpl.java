package com.derry.service.impl;

import com.derry.service.CodeService;
import com.derry.util.MySession;
import com.derry.util.SmsDemo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 15:57 2019/4/29
 * @Modified By:
 */
@Service("codeServiceImpl")
public class CodeServiceImpl implements CodeService{

    @Override
    public Map<String, Object> identifyingCode(String phone, HttpServletRequest request) {
        Map<String, Object>  resMap = new HashMap<>();
        String  number= SmsDemo.createRandomNum(6);
        MySession myc= MySession.getInstance();
        myc.AddSession( request.getSession());
        request.getSession().setAttribute("code", number);
        String  sessionId = request.getSession().getId();
        resMap.put("sessionId",sessionId);
        System.out.println("sessionId为"+sessionId);
        try {
            SmsDemo.sendSms(phone,number);
            System.out.println(number);
            resMap.put("msg","succ");
        } catch (Exception e) {
            resMap.put("msg","fail");
            e.printStackTrace();

        }
        return resMap;
    }
}
