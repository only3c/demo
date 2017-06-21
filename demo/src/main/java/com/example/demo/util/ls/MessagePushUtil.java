package com.example.demo.util.ls;

public class MessagePushUtil {

    /*
     * 公用消息
     */
    public static final String THIRDAPP_AGREE = "已签批";
    public static final String THIRDAPP_REFUSE = "已拒绝";
    public static final String THIRDAPP_TASKID = "taskId";
    public static final String THIRDAPP_OLD = "oId";
    public static final String THIRDAPP_WORKFLOWTYPE = "公文类型";
    public static final String THIRDAPP_PROCESSNODE = "流程节点";
    public static final String THIRDAPP_BATCHTIME = "签批时间";
    public static final String THIRDAPP_REMINDTIME = "催办时间";
    public static final String THIRDAPP_REMINDINFO = "内容";
    public static final String PUSHTYPE = "审批";
    public static final String DOCUMENT_TITLEHEADER = "公文 ";
    public static final String DOCUMENT_TITLETAIL = " 待您审批";
    public static final String THIRDAPP_REMIND = "催促您办理";

    /*
     * 发文
     */
    public static final String THIRDAPP_DISPATCHING = "发文";
    // 待办消息体
    public static final String DISPATCHING_NODENAME = "流程节点";
    public static final String DISPATCHING_URGENCY = "缓急";
    public static final String DISPATCHING_SENDTIME = "发送时间";

    /*
     *收文
     */
    public static final String THIRDAPP_RECIVEDISPATCHING = "收文";
    // 待办消息体
    public static final String RECIVE_NODENAME = "流程节点";
    public static final String RECIVE_URGENCY = "密级程度";
    public static final String RECIVE_ARTICLEBYORG = "来文单位";
    public static final String RECIVE_SENDTIME = "发送时间";
    // 待办分发
    public static final String THIRDAPP_CIRCULATION = "收文传阅";
    public static final String RECIVE_CIRCULATION = "待您查阅";

    /*
     *签报
     */
    public static final String THIRDAPP_SIGNDOCUMENT = "签报";
    // 待办消息体
    public static final String SIGN_NODENAME = "流程节点";
    public static final String SIGN_SENDTIME = "发送时间";
    public static final String SIGN_THIRDAPP_CIRCULATION = "签报传阅";
    public static final String SIGN_ARTICLEBYORG = "签报单位";
    public static final String SIGN_CIRCULATION = "待您查阅";

    /*
     * 请假
     */
    public static final String holidayPushTitle = "的请假需要您审批";
    // 应用消息 请假分类
    public static final String HOLIDAY_TYPE_PERSONALLEAVE = "事假";
    public static final String HOLIDAY_TYPE_SICKLEAVE = "病假";
    public static final String HOLIDAY_TYPE_ANNUALLEAVE = "年假";
    public static final String HOLIDAY_TYPE_TAKEWORKINGDAYSOFF = "调休";
    public static final String HOLIDAY_TYPE_MARITALLEAVE = "婚假";
    public static final String HOLIDAY_TYPE_MATERNITYLEAVE = "产假";
    public static final String HOLIDAY_TYPE_PATERNITYLEAVE = "陪产假";
    public static final String HOLIDAY_TYPE_JOURNEYLEAVE = "路途假";
    public static final String HOLIDAY_TYPE_OTHER = "其他";
    //请假发送待办和办结用
    public static final String THIRDAPP_HOLIDAY = "请假审批";
    public static final String HOLIDAY_APPROVAL = "通过了您的请假";
    public static final String HOLIDAY_REFUSE = "拒绝了您的请假";
    public static final String HOLIDAY_TYPE = "请假类型";
    public static final String HOLIDAY_STARTDATE = "开始时间";
    public static final String HOLIDAY_ENDDATE = "结束时间";
    public static final String HOLIDAY_APPROVALDATE = "审批时间";

    /*
     * 日程
     */
    public static final String SCHEDULETITLE = "您有一个日程提醒";
    public static final String SCHEDULETYPEE = "提醒类型";
    public static final String SCHEDULETIME = "开始时间";
    public static final String SCHEDULECONTENT = "内容";
}
