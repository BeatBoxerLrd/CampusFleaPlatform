package com.derry.util;

import com.derry.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 21:02 2019/5/9
 * @Modified By:
 */
public class UserStatus {
    //获取用户状态
    public static User getStatus(){

        try{

            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            User user=(User) session.getAttribute("currentUser");
            if(user ==null){
                return  null;
            }
            return user;
        }catch (Exception e){
            return null;
        }

    }
    //退出登录
    public static Boolean lagout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return true;
    }

}
