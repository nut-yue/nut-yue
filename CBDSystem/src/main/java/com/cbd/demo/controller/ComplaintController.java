package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.BillBean;
import com.cbd.demo.bean.ComplaintBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.ComplaintEntity;
import com.cbd.demo.service.IComplaintService;
import com.cbd.demo.util.DateUtils;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : ComplaintController
 * @Date ：2019/6/2 15:04
 * @Desc ：类的介绍：投诉页面控制器
 * @author：作者：峗权
 */

@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    @Autowired
    private IComplaintService complaintService;

    /**
     * 根据个人用户id查看该用户发起投诉的投诉信息,分页条件查询，
     * 如果是管理员，传入-1
     * @param currentPage 当前页
     * @param pageSize 显示条数
     * @param oldDate 起始时间
     * @param newDate 结束时间
     * @return
     */
    @Introduce(desc="根据个人ID查看投诉信息集合")
    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    public ResponseData findComplaintAll(int userId,int currentPage, int pageSize,
                                         String oldDate, String newDate, HttpSession session){
        IPage<ComplaintBean> page = new Page<ComplaintBean>();
        //证明是管理员
        if(userId==-1){
            page= complaintService.findComplaintAll(userId,currentPage,pageSize,oldDate,newDate);
        }else{
            UserBean userBean = (UserBean) session.getAttribute("login");
            page= complaintService.findComplaintAll(userBean.getUserId(),currentPage,pageSize,oldDate,newDate);
        }
        if(page!=null){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("page",page);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 查看单个投诉记录
     * @param id 投诉记录id
     * @return
     */
    @Introduce(desc="查看单个投诉记录")
    @RequestMapping(value = "/getOne",method = RequestMethod.GET)
    public ResponseData getComplaint(int id){
        ComplaintBean complaintBean = complaintService.getComplaint(id);
        if(complaintBean!=null){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("complaint",complaintBean);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 添加投诉
     * @param complaint 投诉对象
     * @return
     */
    @Introduce(desc="添加投诉")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResponseData saveComplaint(ComplaintBean complaint, int billId, int userId, int respondentUserId){
        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        complaint.setUserBean(userBean);

        UserBean userBean1 = new UserBean();
        userBean1.setUserId(respondentUserId);
        complaint.setRespondentUserBean(userBean1);

        BillBean billBean = new BillBean();
        billBean.setBillId(billId);
        complaint.setBillBean(billBean);

        complaint.setComplaintDate(DateUtils.getDate());

        int num = complaintService.saveComplaint(complaint);

        if(num!=0){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("num",num);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 修改投诉信息
     * @param complaint 投诉信息对象
     * @return
     */

    @Introduce(desc="修改投诉信息")
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ResponseData updateComplaint(ComplaintBean complaint){
        int num = complaintService.updateComplaint(complaint);
        if(num!=0){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("num",num);
            return responseData;
        }
        return ResponseData.notFound();
    }

    /**
     * 根据账单ID投诉记录
     * @param billId 投诉记录id
     * @return
     */

    @Introduce(desc="根据账单ID投诉记录")
    @RequestMapping(value = "/getByBillId",method = RequestMethod.GET)
    public ResponseData getComplaintBean(int billId){
        ComplaintBean complaintBean = complaintService.getComplaintBean(billId);
        if(complaintBean!=null){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("complaint",complaintBean);
            return responseData;
        }
        return ResponseData.notFound();
    }

}
