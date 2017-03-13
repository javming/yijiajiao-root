package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.ResultBean;
import com.yijiajiao.root.bean.command.*;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import com.yijiajiao.root.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.SALE_SERVER;
import static com.yijiajiao.root.utils.RootUtil.WARES_SERVER;

@Service("baseDataLogic")
public class BaseDataLogic{

	private static final Logger log = LoggerFactory.getLogger(BaseDataLogic.class);

	/**
	 * 手动组卷添加卷头
	 */
	public String CreateExam(String params) {
		String createExam = Config.getString("createExam");
		CreateExamBean createExamBean = JSON.parseObject(params, CreateExamBean.class);
		return HttpUtil.httpRest(WARES_SERVER, createExam, null, createExamBean, "POST");

	}

	/**
	 * 手动组卷添加试题
	 */
	public String CreateExamDetail(String params) {
		String createExamDetail = Config.getString("CreateExamDetail");
		CreateExamDetailBean createExamDetailBean = JSON.parseObject(params, CreateExamDetailBean.class);
		return HttpUtil.httpRest(WARES_SERVER, createExamDetail, null, createExamDetailBean, "POST");

	}

	/**
	 *  智能组卷
	 */
	public String SmartCreateExam(String params) {
		String smartCreateExam = Config.getString("SmartCreateExam");
		SmartCreateExamBean smartCreateExamBean = JSON.parseObject(params, SmartCreateExamBean.class);
		return HttpUtil.httpRest(WARES_SERVER, smartCreateExam, null, smartCreateExamBean, "POST");
	}

	public String AddQuestions(String params) {
		String AddQuestions = Config.getString("AddQuestions");
		AddQuestionsBean addQuestionsBean = JSON.parseObject(params, AddQuestionsBean.class);
		return HttpUtil.httpRest(WARES_SERVER, AddQuestions, null, addQuestionsBean, "POST");
	}
	
	public String markingPaper(String params) {
		String markingPaper = Config.getString("markingPaper");
		DiagnoseAnswerSubmitBean diagnoseAnswerSubmitBean = JSON.parseObject(params, DiagnoseAnswerSubmitBean.class);
		String res = HttpUtil.httpRest(WARES_SERVER, markingPaper, null, diagnoseAnswerSubmitBean, "POST");
		ResultBean result = JSON.parseObject(res, ResultBean.class);
		if(result.getCode()!=200){
			return res;
		}
		if (diagnoseAnswerSubmitBean.getSubmitType()==null){
			log.info("修改学生课程状态ishomework为 2");
			String updateIsHomework=Config.getString("updateIsHomework")+"openId="+diagnoseAnswerSubmitBean.getOpenId()+
					"&commodityId="+diagnoseAnswerSubmitBean.getWaresId()+"&slaveId="+
					(StringUtil.isEmpty(diagnoseAnswerSubmitBean.getWaresSlaveId())?-1:diagnoseAnswerSubmitBean.getWaresSlaveId());
			HttpUtil.httpRest(SALE_SERVER,updateIsHomework,null,null,"PUT");
		}
		return res;
	}

}
