package com.yijiajiao.root.command.service;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.*;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

@Service("solutionLogic")
public class SolutionLogic extends BasicLogic{

	public String updateAsk(String params) {
		String updateAsk = Config.getString("updateAsk");
		UpdateAskBean updateAskBean = JSON.parseObject(params, UpdateAskBean.class);
		return RabbitmqUtil.httpRest(solution_server, updateAsk, null, updateAskBean, "POST");
	}

	public String updateAnswer(String params) {
		String updateAnswer = Config.getString("updateAnswer");
		UpdateAnswerBean updateAnswerBean = JSON.parseObject(params, UpdateAnswerBean.class);
		return RabbitmqUtil.httpRest(solution_server, updateAnswer, null, updateAnswerBean, "POST");
	}

	public String addDoubt(String params) {
		String addDoubt = Config.getString("addDoubt");
		AddDoubtBean addDoubtBean = JSON.parseObject(params, AddDoubtBean.class);
		return RabbitmqUtil.httpRest(solution_server, addDoubt, null, addDoubtBean, "POST");
	}

	public String updateDoubt(String params) {
		String updateDoubt = Config.getString("updateDoubt");
		AddDoubtBean updateDoubtBean = JSON.parseObject(params, AddDoubtBean.class);
		return RabbitmqUtil.httpRest(solution_server, updateDoubt, null, updateDoubtBean, "POST");
	}

	public String addComplain(String params) {
		String addComplain = Config.getString("addComplain");
		AddComplainBean addComplainBean = JSON.parseObject(params, AddComplainBean.class);
		return RabbitmqUtil.httpRest(solution_server, addComplain, null, addComplainBean, "POST");
	}

	public String reBackComplain(String params) {
		String reBackComplain = Config.getString("reBackComplain");
		ReBackComplainBean reBackComplainBean = JSON.parseObject(params, ReBackComplainBean.class);
		return RabbitmqUtil.httpRest(solution_server, reBackComplain, null, reBackComplainBean, "POST");
	}

	public String addTimePakage(String params) {
		String path = Config.getString("addtimepakage");
		AddTimePakageBean addTimePakageBean = JSON.parseObject(params, AddTimePakageBean.class);
		return RabbitmqUtil.httpRest(solution_server, path, null, addTimePakageBean, "POST");
	}

	public String solutionAppraise(String params) {
		String path =Config.getString("solutionAppraise");
		AppraiseSolutionBean appraiseSolutionBean = JSON.parseObject(params, AppraiseSolutionBean.class);
		return RabbitmqUtil.httpRest(solution_server, path, null, appraiseSolutionBean, "POST");
	}

	public String solutionFeedBack(String params) {
		String path = Config.getString("solutionFeedBack");
		SolutionFeedBackBean solutionFeedBackBean = JSON.parseObject(params, SolutionFeedBackBean.class);
		return RabbitmqUtil.httpRest(solution_server, path, null, solutionFeedBackBean, "POST");
	}

}
