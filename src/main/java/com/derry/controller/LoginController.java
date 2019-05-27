package com.derry.controller;

import com.alibaba.fastjson.JSON;
import com.derry.dao.ShiroDao;
import com.derry.pojo.User;
import com.derry.sercurity.UserNamePasswordTelphoneToken;
import com.derry.service.CodeService;
import com.derry.service.UserService;
import com.derry.util.MySession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class LoginController {
    @Autowired
    private CodeService codeService;
    @Autowired
    private ShiroDao shiroDao;
    @Autowired
    private UserService userService;

    /**
     * @Author：LiuRuidong
     * @Description:使用用户名和密码登录
     * @Date:2019/5/1 12:20
     * @Param:
     * @return
     */
    @RequestMapping("/dologin")
    @ResponseBody
    public void dologin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response,HttpSession session){
        Map<String ,Object> resList = new HashMap<>();
        String callback = request.getParameter("callback");
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {

            currentUser.login(token);
            MySession myc= MySession.getInstance();
            myc.AddSession( request.getSession());
            User loginUser  = userService.getUserByPhone(username);
            request.getSession().setAttribute("loginuser",loginUser);
            //request.getSession().setAttribute("userTelphone",username);
            String  sessionId = request.getSession().getId();
            token.setRememberMe(true);
            User user = (User)currentUser.getPrincipal();
            System.out.println("subject:"+currentUser);

            //session.setAttribute("user",user);
            //String username =  (User)session.getAttribute("user").getClass().getDeclaredField("username");
            System.out.println("user:"+user);
            System.out.println("登录完成");

            resList.put("sessionId",sessionId);
            resList.put("code","true");
            String json = JSON.toJSONString(resList);
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = null;
            pw = response.getWriter();
            if ((callback != "") && (callback != null)) {
                json = callback + "(" + json + ")";
            }
            System.out.println(json);
            pw.println(json);
            pw.flush();
        } catch (Exception e) {
            resList.put("code","false");
        }
    }
    /**
     * @Author：LiuRuidong
     * @Description: 获取手机验证码
     * @Date:2019/5/1 12:20
     * @Param:
     * @return
     */
    @RequestMapping("/getTelCode")
    @ResponseBody
    public void selectUser(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        String callback = request.getParameter("callback");
        Map<String ,Object> resList = new HashMap<>();
        //得到手机号
        String phone=request.getParameter("userPhone");
        //首先需要根据该手机号去查询用户信息
        User user = shiroDao.getUserByUserName(phone);
        //session.setAttribute("user",user);
        if(user == null){
            resList.put("status","none");
        }else{
            resList =  codeService.identifyingCode(phone ,request);
            resList.put("status","done");
        }
        String json = JSON.toJSONString(resList);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            if ((callback!="")&&(callback!=null)){
                json=callback+"("+json+")";
            }
            System.out.println(json);
            pw.println(json);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Author：LiuRuidong
     * @Description:使用手机号和验证码登录
     * @Date:2019/5/1 12:20
     * @Param:
     * @return
     */
    @RequestMapping("/plogin")
    @ResponseBody
    public void pLogin(@RequestParam("phone") String phone, @RequestParam("code") String code, @RequestParam("sessionId")String sessionId, HttpServletRequest request,HttpSession session ,HttpServletResponse response) {
        Map<String ,Object> resList = new HashMap<>();
        String callback = request.getParameter("callback");
        Subject subject = SecurityUtils.getSubject();
        UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(phone,code,sessionId);
        // 根据phone从session中取出发送的短信验证码，并与用户输入的验证码比较
        try {
            subject.login(token);
            User user = (User)subject.getPrincipal();
            MySession myc= MySession.getInstance();
            myc.AddSession( request.getSession());
            User loginUser  = userService.getUserByPhone(phone);
            request.getSession().setAttribute("loginuser",loginUser);
            //request.getSession().setAttribute("userTelphone",phone);
            String  sessionId1 = request.getSession().getId();
            resList.put("sessionId",sessionId1);
            System.out.println("登录完成");
            resList.put("code","true");
            String json = JSON.toJSONString(resList);
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = null;
            pw = response.getWriter();
            if ((callback != "") && (callback != null)) {
                json = callback + "(" + json + ")";
            }
            System.out.println(json);
            pw.println(json);
            pw.flush();
        } catch (Exception e) {
            resList.put("code","false");
        }
    }
}
