package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.*;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import com.yijiajiao.rabbitmq.util.RedisUtil;
import com.yijiajiao.rabbitmq.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("baseDataLogic")
public class BaseDataLogic extends BasicLogic{

	private static final Logger log = LoggerFactory.getLogger(BaseDataLogic.class);

	/**
	 * 手动组卷添加卷头
	 */
	public String CreateExam(String params) {
		String createExam = Config.getString("createExam");
		CreateExamBean createExamBean = JSON.parseObject(params, CreateExamBean.class);
		return RabbitmqUtil.httpRest(wares_server, createExam, null, createExamBean, "POST");

	}

	/**
	 * 手动组卷添加试题
	 */
	public String CreateExamDetail(String params) {
		String createExamDetail = Config.getString("CreateExamDetail");
		CreateExamDetailBean createExamDetailBean = JSON.parseObject(params, CreateExamDetailBean.class);
		return RabbitmqUtil.httpRest(wares_server, createExamDetail, null, createExamDetailBean, "POST");

	}

	/**
	 *  智能组卷
	 */
	public String SmartCreateExam(String params) {
		String smartCreateExam = Config.getString("SmartCreateExam");
		SmartCreateExamBean smartCreateExamBean = JSON.parseObject(params, SmartCreateExamBean.class);
		return RabbitmqUtil.httpRest(wares_server, smartCreateExam, null, smartCreateExamBean, "POST");
	}

	public String AddQuestions(String params) {
		String AddQuestions = Config.getString("AddQuestions");
		AddQuestionsBean addQuestionsBean = JSON.parseObject(params, AddQuestionsBean.class);
		return RabbitmqUtil.httpRest(wares_server, AddQuestions, null, addQuestionsBean, "POST");
	}
	
	public String markingPaper(String tag,String params) {
		String markingPaper = Config.getString("markingPaper");
		DiagnoseAnswerSubmitBean diagnoseAnswerSubmitBean = JSON.parseObject(params, DiagnoseAnswerSubmitBean.class);
		String res = RabbitmqUtil.httpRest(wares_server, markingPaper, null, diagnoseAnswerSubmitBean, "POST");
		ResultBean result = JSON.parseObject(res, ResultBean.class);
		if(result.getCode()!=200){
			return res;
		}
		if (diagnoseAnswerSubmitBean.getSubmitType()==null){
			log.info("修改学生课程状态ishomework为 2");
			String updateIsHomework=Config.getString("updateIsHomework")+"openId="+diagnoseAnswerSubmitBean.getOpenId()+
					"&commodityId="+diagnoseAnswerSubmitBean.getWaresId()+"&slaveId="+
					(StringUtil.isEmpty(diagnoseAnswerSubmitBean.getWaresSlaveId())?-1:diagnoseAnswerSubmitBean.getWaresSlaveId());
			RabbitmqUtil.httpRest(sale_server,updateIsHomework,null,null,"PUT");
		}
		RedisUtil.setEx(tag, 36000, res);
		return JSON.toJSONString(ResultBean.getSucResult(tag));
	}

}
