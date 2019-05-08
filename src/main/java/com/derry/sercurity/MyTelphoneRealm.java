package com.derry.sercurity;

import com.derry.pojo.Permission;
import com.derry.pojo.User;
import com.derry.service.ShiroService;
import com.derry.util.MySession;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 9:01 2019/4/29
 * @Modified By:
 */
public class MyTelphoneRealm extends AuthorizingRealm {
    @Resource
    private ShiroService shiroService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {

        //获取user对象
        User user  = (User) pc.getPrimaryPrincipal();
        //保存该用户的权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取permission
        if(user!=null) {
            List<Permission> permissionsByUser = shiroService.getPermissionsByUser(user);
            if (permissionsByUser.size()!=0) {
                for (Permission p: permissionsByUser) {
                    info.addStringPermission(p.getUrl());
                }
                return info;
            }
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进来认证了");
        UserNamePasswordTelphoneToken userNamePasswordTelphoneToken = (UserNamePasswordTelphoneToken)token;
        //用户名为手机号
        System.out.println("1："+(userNamePasswordTelphoneToken.getPhone()));
        User user  = shiroService.getUserByUserName(userNamePasswordTelphoneToken.getPhone());
        System.out.println("1!");
        String sessionId = userNamePasswordTelphoneToken.getSessionId();
        String code = (String )MySession.getInstance().getSession(sessionId).getAttribute("code");
        if(user == null) {
            return null;
        }
        //最后的比对需要交给安全管理器
        //三个参数进行初步的简单认证信息对象的包装
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, code, this.getClass().getSimpleName());
        return info;

    }

    @Override
    public boolean supports(AuthenticationToken var1){
        return var1 instanceof UserNamePasswordTelphoneToken;
    }
}
