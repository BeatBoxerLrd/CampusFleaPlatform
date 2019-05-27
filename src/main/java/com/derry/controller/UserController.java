package com.derry.controller;

import com.alibaba.fastjson.JSON;
import com.derry.pojo.User;
import com.derry.service.UserService;
import com.derry.util.MySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 13:43 2019/5/11
 * @Modified By:
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private  UserService userService;
    @RequestMapping("/getUserName")
    @ResponseBody
    public void getUserName(@RequestParam("sessionId") String sessionId, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String ,Object> resList = new HashMap<>();
        String callback = request.getParameter("callback");

        User user = null;
        try {
            user = (User) MySession.getInstance().getSession(sessionId).getAttribute("loginuser");
        }catch (Exception e){
            user = null;
        }

        if(user!=null){
            String userName = user.getUsername();
            resList.put("username",userName);
            resList.put("code","true");
        }else{
            resList.put("code","false");
        }

        String json = JSON.toJSONString(resList);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (Exception e) {
            //e.printStackTrace();
            resList.put("code","false");
        }
        if ((callback != "") && (callback != null)) {
            json = callback + "(" + json + ")";
        }
        System.out.println(json);
        pw.println(json);
        pw.flush();

    }
}