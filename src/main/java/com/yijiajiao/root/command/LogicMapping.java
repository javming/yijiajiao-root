package com.yijiajiao.root.command;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.command.LogicEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.yijiajiao.root.command.LogicFactory.*;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-03-09-15:17
 */
public class LogicMapping implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(LogicMapping.class);

    private String cmd;
    private HttpServletResponse response;
    private AsyncContext asyncContext;
    private String params;


    public LogicMapping(String cmd, AsyncContext aCtx,HttpServletResponse response,String bodyParm) {
        this.cmd = cmd;
        this.response = response;
        this.asyncContext = aCtx;
        this.params = bodyParm;
    }

    @Override
    public void run() {
        LogicEnum logic = LogicEnum.valueOf(cmd);
        String backValue;
        switch (logic){
            case UpLoadVedio:
                backValue = wareLogic.uploadVideo(params);
                break;
            case ApplyTeacher:
                backValue = userLogic.applyteacher(params);
                break;
            case SetStore:
                backValue = userLogic.setStore(params);
                break;
            case ApplyPermission:
                backValue = teachLogic.applyPermission(params);
                break;
            case PassTest:
                backValue = teachLogic.passTest(params);
                break;
            case Insertanswerpermission:
                backValue = teachLogic.insertanswerpermission(params);
                break;
            case WareLive:
                backValue = wareLogic.wareLive(params);
                break;
            case WareVideo:
                backValue = wareLogic.wareVideo(params);
                break;
            case WareOne2One:
                backValue = wareLogic.wareOne2One(params);
                break;
            case CommitExam:
                backValue = wareLogic.commitExam(params);
                break;
            case CreateOrder:
                backValue = saleLogic.createOrder(params);
                break;
            case CreateRefund:
                backValue = saleLogic.createRefund(params);
                break;
            case UpdateAppraise:
                backValue = saleLogic.updateAppraise(params);
                break;
            case UpdateAppraiseReback:
                backValue = saleLogic.updateAppraiseReback(params);
                break;
            case Complete:
                backValue = userLogic.complete(params);
                break;
            case Ask:
                backValue = solutionLogic.updateAsk(params);
                break;
            case Answer:
                backValue = solutionLogic.updateAnswer(params);
                break;
            case AddDoubt:
                backValue = solutionLogic.addDoubt(params);
                break;
            case UpdateDoubt:
                backValue = solutionLogic.updateDoubt(params);
                break;
            case AddComplain:
                backValue = solutionLogic.addComplain(params);
                break;
            case ReBackComplain:
                backValue = solutionLogic.reBackComplain(params);
                break;
            case FeedBack:
                backValue = ossLogic.feedBack(params);
                break;
            case BindAliPay:
                backValue = financeLogic.bindAliPay(params);
                break;
            case SetMsg:
                backValue = msgLogic.SetMsg(params);
                break;
            case CreateExam:
                log.info("手动组卷头");
                backValue = baseDataLogic.CreateExam(params);
                break;
            case CreateExamDetail:
                log.info("手动组卷详情");
                backValue = baseDataLogic.CreateExamDetail(params);
                break;
            case AddQuestions:
                log.info("手动组卷添加试题");
                backValue = baseDataLogic.AddQuestions(params);
                break;
            case SmartCreateExam:
                log.info("自动组卷");
                backValue = baseDataLogic.SmartCreateExam(params);
                break;
            case ApplyInterviewTime:
                log.info("申请面试时间");
                backValue = teachLogic.applyInterviewTime(params);
                break;
            case ApplyFacingTeachTime:
                log.info("申请面授时间");
                backValue= teachLogic.applyFacingTeachTime(params);
                break;
            case AddTimePakage:
                log.info("添加答疑时长包");
                backValue = solutionLogic.addTimePakage(params);
                break;
            case SolutionAppraise:
                log.info("答疑评价");
                backValue = solutionLogic.solutionAppraise(params);
                break;
            case SolutionFeedBack:
                log.info("教师端添加反馈理由");
                backValue= solutionLogic.solutionFeedBack(params);
                break;
            case DiagnoseAnswerSubmit:
                log.info("诊断试卷提交答案");
                backValue= teachLogic.diagnoseAnswerSubmit(params);
                break;
            case MarkingPaper:
                log.info("课中练习/课后作业/模拟考交卷");
                backValue= baseDataLogic.markingPaper(tag,params);
                break;
            case UpdateWaresLive:
                log.info("修改课程信息");
                backValue= wareLogic.updateWaresLive(params);
                break;
            case AddCoupon:
                log.info("添加优惠券");
                backValue = promotionLogic.addCoupon(params);
                break;
            case UpdateCoupon:
                log.info("修改优惠券");
                backValue = promotionLogic.updateCoupon(params);
                break;
            case AddActivity:
                log.info("添加活动");
                backValue = promotionLogic.addActivity(params);
                break;
            case UpdateActivity:
                log.info("修改活动");
                backValue = promotionLogic.updateActivity(params);
                break;
            default:
                log.info("没有匹配该请求的路径：cmd="+cmd);
                backValue= JSON.toJSONString(ResultBean.getFailResult(404,"没有匹配该请求的路径!"));
                break;
        }
        PrintWriter out = null;
        log.info("__其他系统返回：\n  "+backValue);
        try {
            out = response.getWriter();
            out.print(backValue);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out != null){
                out.close();
            }
        }
    }
}
