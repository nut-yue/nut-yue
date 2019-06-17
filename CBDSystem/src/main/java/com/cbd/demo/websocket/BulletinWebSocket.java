package com.cbd.demo.websocket;

import com.cbd.demo.bean.UserBean;
import com.cbd.demo.service.IUserService;
import com.cbd.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ServerEndpoint 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。
 * 类似Servlet的注解mapping。无需在web.xml中配置。
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入。
 * @Author jiangpeng
 */
@ServerEndpoint(value = "/webSocket/cbd/{type}/{userid}") //该注解表示该类被声明为一个webSocket终端
@Component
public class BulletinWebSocket {
    //webSocket中无法直接注入
    private static ApplicationContext applicationContext;
    private IUserService userService;
    public static void setApplicationContext(ApplicationContext applicationContext) {
        BulletinWebSocket.applicationContext = applicationContext;
    }

    /**
     * 初始在线人数
     */
    private static int online_num = 0;
    /**
     * 线程安全的socket集合
     */
//    private static CopyOnWriteArraySet<BulletinWebSocket> webSocketSet = new CopyOnWriteArraySet<BulletinWebSocket>();
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();//存储所有userid及对应的session （存储普通用户和企业用户）

    private static Map<String, Session> adminSession = new HashMap<String, Session>();//存储管理员的session

    private static Map<String, Session> numSession = new HashMap<String, Session>();//存储



    /**
     * 会话
     */
    private Session session;

    /**
     * 通知监听人
     */
    public void sendMessages() {
        Session session = numSession.get("0");
        if (session != null) {
            session.getAsyncRemote().sendText(getOnline_num() + "");
        }
    }

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userid") String userid,
                       @PathParam(value = "type") String type) {
        this.session = session;
        if ("admin".equals(type)) {
            //管理员
            adminSession.put(userid, session);
        } else if ("num".equals(type)){
            numSession.put(userid, session);
            this.session.getAsyncRemote().sendText(getOnline_num()+"");
        } else if("user".equals(type)){
            //普通用户, 企业用户
            System.out.println("userId"+userid);
            sessionPool.put(userid, session);
        }
        setOnlineCount();

        if (!userid.equals("0")) {
            sendMessages();
        }
        System.out.println(getOnline_num()+"当前在线人数----");
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose( @PathParam(value = "userid") String userid,
                        @PathParam(value = "type") String type) {

        if ("admin".equals(type)) {
            //管理员
            adminSession.remove(userid, session);
        } else if ("num".equals(type)){
            numSession.remove(userid, session);
        } else {
            //普通用户, 企业用户
            sessionPool.remove(userid, session);
        }
        setOnlineCount();
        if (!userid.equals("0")) {
            sendMessages();
        }

    }

    /**
     * 前端发送的数据
     *
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:"+message);
        String[] split = message.split("=");
        IUserService userService = (IUserService) applicationContext.getBean(IUserService.class);
        UserBean userBean = userService.getUserId(Integer.parseInt(split[1]));
        System.out.println(userBean);
        System.out.println("西" + userBean.getAdminBean().getAdminId());
        Session session1 = sessionPool.get(userBean.getAdminBean().getAdminId()+"");
        System.out.println(sessionPool.size());
        System.out.println(sessionPool.toString());
        System.out.println(session1);
        if (session1 != null) {

            session1.getAsyncRemote().sendText(split[0]+"");
        }

    }

    public synchronized int getOnline_num() {
        return BulletinWebSocket.online_num;
    }

    public synchronized int setOnlineCount() {
        BulletinWebSocket.online_num = sessionPool.size() + adminSession.size();
        return  BulletinWebSocket.online_num;
    }

}
