package com.derry.listener;

import com.derry.util.MySession;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:LiuRuidong
 * @Description:
 * @Date: Created in 16:07 2019/4/29
 * @Modified By:
 */
public class SessionListener implements HttpSessionListener {
    public static Map userMap = new HashMap();
    private MySession myc= MySession.getInstance();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        myc.AddSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        myc.DelSession(session);
    }
}
