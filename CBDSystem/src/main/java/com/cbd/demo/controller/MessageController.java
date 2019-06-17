package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.MessageBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.MessageEntity;
import com.cbd.demo.service.IMessageService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName : MessageContoller
 * @Date ：2019/6/1 21:52
 * @Desc ：类的介绍： 短消息的controller
 * @author：作者：胡平
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;


    /**
     * 查询单个短消息
     * @param messageId
     * @return
     */
    @RequestMapping(value = "findById", method = RequestMethod.GET)
    public ResponseData findByIdMessage(int messageId) {
        MessageBean messageBean = messageService.getByIdMessage(messageId);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("message", messageBean);
        return responseData;
    }

    /**
     * 添加
     * @param messageBean
     * @return
     */
    @Introduce(desc = "添加短消息")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData saveMessage(MessageBean messageBean, int getUserId, HttpSession session) {
        UserBean postUserBean = (UserBean) session.getAttribute("login");

        UserBean getUserBean = new UserBean();
        getUserBean.setUserId(getUserId);
        messageBean.setMessageGetUserBean(getUserBean);
        messageBean.setMessagePostUserBean(postUserBean);
        int i = messageService.saveMessage(messageBean);
        ResponseData responseData = null;
        if (i == 1) {
            responseData = new ResponseData();
            responseData.setMessage("添加成功");
            return responseData;
        }
        responseData = new ResponseData();
        responseData.setMessage("添加失败");
        responseData.setCode(500);
        return responseData;
    }

    /**
     * 查询自己的接受的消息
     *
     * @param messageIsRead    是否阅读、如果需要查询所有的消息、messageIsRead传入-1（注意：不明白问作者
     *                         需要该条件、0未阅读/1已阅读
     * @param currentPage      当前页
     * @param pageSize         每页显示条数
     * @return
     */
    @Introduce(desc = "查询自己的接受的消息")
    @RequestMapping(value = "myTakeMessage", method = RequestMethod.GET)
    public ResponseData listMyTakeMessage(int currentPage, int pageSize,
                                          int messageIsRead, HttpSession session) {
        UserBean userBean = (UserBean) session.getAttribute("login");

//        userBean.setUserId(1); //如果登陆功能完成，删除这句即可

        IPage<MessageEntity> messages =
                messageService.listMyTakeMessage(currentPage, pageSize,
                       userBean.getUserId(), messageIsRead);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("messages", messages);
        return responseData;
    }


    /**
     * 查询自己的发送的短消息
     *
     * @param messageIsRead     是否阅读、如果需要查询所有的消息、messageIsRead传入-1（注意：不明白问作者
     *                          需要该条件、0未阅读/1已阅读
     * @param currentPage       当前页
     * @param pageSize          每页显示条数
     * @return
     */
    @Introduce(desc = "查询自己的发送的消息")
    @RequestMapping(value = "mySendMessage", method = RequestMethod.GET)
    public ResponseData listMySendMessage(int currentPage, int pageSize,
                                         int messageIsRead, HttpSession session) {
        UserBean userBean = (UserBean) session.getAttribute("login");


        IPage<MessageEntity> messages =
                messageService.listMySendMessage(currentPage, pageSize,
                        userBean.getUserId(), messageIsRead);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("messages", messages);
        return responseData;
    }

    /**
     * 批量删除
     * @param messageId 传递参数的格式 "1,2,3,4"注意必须这种格式,我在controller中通过逗号分隔的
     * @return
     */
    @Introduce(desc = "批量删除短消息")
    @RequestMapping(value = "deleteMessages", method = RequestMethod.GET)
    public ResponseData deleteMessages(String messageId) {
        Integer[] messageIds = stringArrTurnIntArr(messageId);
        ResponseData responseData = null;
        if (messageIds.length > 0) {
            List<Integer> integers = Arrays.asList(messageIds);
            messageService.deleteMessages(integers);
            responseData = new ResponseData();
            responseData.setMessage("删除成功");
            return responseData;
        }
        responseData = new ResponseData();
        responseData.setMessage("参数不对");
        responseData.setCode(404);
        return responseData;
    }

    /**
     * 批量修改短消息的状态
     * @param messageidStr 传递参数的格式 （1,2,3,4)注意必须这种格式,我在controller中通过逗号分隔的
     * @param messageIsRead
     * @return
     */
    @Introduce(desc = "批量修改短消息的状态")
    @RequestMapping(value = "updateMessages", method = RequestMethod.GET)
    public ResponseData updateMessages(String messageidStr, int messageIsRead) {
        Integer[] messageIds = stringArrTurnIntArr(messageidStr);
        ResponseData responseData = null;
        if (messageIds.length > 0) {
            List<Integer> integers = Arrays.asList(messageIds);
            messageService.updateMessages(integers, messageIsRead);
            responseData = new ResponseData();
            responseData.setMessage("修改成功");
            return responseData;
        }
        responseData = new ResponseData();
        responseData.setMessage("参数不对");
        responseData.setCode(404);
        return responseData;
    }

    /**
     * String数组转为int数组
     * @param str
     * @return
     */
    private Integer[] stringArrTurnIntArr(String str) {
        String[] strs = str.split(",");
        Integer[] ints = new Integer[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = Integer.parseInt(strs[i].trim());
        }
        return ints;
    }
}
