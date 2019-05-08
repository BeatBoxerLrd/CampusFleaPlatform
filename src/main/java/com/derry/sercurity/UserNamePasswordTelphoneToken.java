package com.derry.sercurity;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class UserNamePasswordTelphoneToken implements  HostAuthenticationToken, RememberMeAuthenticationToken, Serializable {

    // 手机号码
    private String phone;
    private String sessionId;
    private boolean rememberMe;
    private String host;
    private String code;

    /**
     * 重写getPrincipal方法
     */
    @Override
    public Object getPrincipal() {
        return this.getPhone();
    }

    /**
     * 重写getCredentials方法
     */
    @Override
    public Object getCredentials() {
        return this.getCode();
    }


    public UserNamePasswordTelphoneToken() { this.rememberMe = false; }

    public UserNamePasswordTelphoneToken(String phone, String code, String sessionId ) { this(phone,code, sessionId,false, null); }

    public UserNamePasswordTelphoneToken(String phone, String code,String sessionId,boolean rememberMe) { this(phone,code, sessionId,rememberMe, null); }

    public UserNamePasswordTelphoneToken(String phone, String code,String sessionId,boolean rememberMe, String host) {
        this.phone = phone;
        this.code = code;
        this.sessionId = sessionId;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }
}
