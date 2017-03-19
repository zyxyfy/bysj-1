package com.newview.bysj.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Hashtable;
import java.util.Map;

/**
 * 监听当前系统的在线人数
 * Created 2016/4/17,10:37.
 * Author 张战.
 */
public class OnlineListener implements HttpSessionListener {

    //private static final Logger logger = Logger.getLogger(OnlineListener.class);


    /**
     * 用户与服务器的会话开始时触发该方法
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession httpsession = httpSessionEvent.getSession();
        ServletContext application = httpsession.getServletContext();
        //获取当前会话对应的id
        String sessionId = httpsession.getId();
        //判断当前是不是一个新的会话
        if (httpsession.isNew()) {
            String user = (String) httpsession.getAttribute("user");
            user = (user == null) ? "游客" : user;
            //获取当前正在访问系统的集合
            Map<String, String> online = (Map<String, String>) application.getAttribute("online");
            if (online == null) {
                online = new Hashtable<>();
            }
            //将当前用户添加到集合中
            online.put(sessionId, user);
            application.setAttribute("online", online);
        }
    }


    /**
     * 用户与服务器的会话结束时触发该方法
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //得到当前的session
        HttpSession httpSession = httpSessionEvent.getSession();
        //得到当前的上下文
        ServletContext application = httpSession.getServletContext();
        //获取session的id
        String sessionId = httpSession.getId();
        Map<String, String> online = (Map<String, String>) application.getAttribute("online");
        if (online != null) {
            //删除该用户的在线信息
            online.remove(sessionId);
        }
        application.setAttribute("online", online);
    }
}
