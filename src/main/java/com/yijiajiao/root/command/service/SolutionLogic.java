package com.yijiajiao.root.command.service;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.*;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.SOLUTION_SERVER;

@Service("solutionLogic")
public class SolutionLogic {

	public String updateAsk(String params) {
		String updateAsk = Config.getString("updateAsk");
		UpdateAskBean updateAskBean = JSON.parseObject(params, UpdateAskBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, updateAsk, null, updateAskBean, "POST");
	}

	public String updateAnswer(String params) {
		String updateAnswer = Config.getString("updateAnswer");
		UpdateAnswerBean updateAnswerBean = JSON.parseObject(params, UpdateAnswerBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, updateAnswer, null, updateAnswerBean, "POST");
	}

	public String addDoubt(String params) {
		String addDoubt = Config.getString("addDoubt");
		AddDoubtBean addDoubtBean = JSON.parseObject(params, AddDoubtBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, addDoubt, null, addDoubtBean, "POST");
	}

	public String updateDoubt(String params) {
		String updateDoubt = Config.getString("updateDoubt");
		AddDoubtBean updateDoubtBean = JSON.parseObject(params, AddDoubtBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, updateDoubt, null, updateDoubtBean, "POST");
	}

	public String addComplain(String params) {
		String addComplain = Config.getString("addComplain");
		AddComplainBean addComplainBean = JSON.parseObject(params, AddComplainBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, addComplain, null, addComplainBean, "POST");
	}

	public String reBackComplain(String params) {
		String reBackComplain = Config.getString("reBackComplain");
		ReBackComplainBean reBackComplainBean = JSON.parseObject(params, ReBackComplainBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, reBackComplain, null, reBackComplainBean, "POST");
	}

	public String addTimePakage(String params) {
		String path = Config.getString("addtimepakage");
		AddTimePakageBean addTimePakageBean = JSON.parseObject(params, AddTimePakageBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, path, null, addTimePakageBean, "POST");
	}

	public String solutionAppraise(String params) {
		String path =Config.getString("solutionAppraise");
		AppraiseSolutionBean appraiseSolutionBean = JSON.parseObject(params, AppraiseSolutionBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, path, null, appraiseSolutionBean, "POST");
	}

	public String solutionFeedBack(String params) {
		String path = Config.getString("solutionFeedBack");
		SolutionFeedBackBean solutionFeedBackBean = JSON.parseObject(params, SolutionFeedBackBean.class);
		return HttpUtil.httpRest(SOLUTION_SERVER, path, null, solutionFeedBackBean, "POST");
	}

}
