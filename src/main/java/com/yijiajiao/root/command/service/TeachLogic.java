package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.*;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.TEACH_SERVER;

@Service("teachLogic")
public class TeachLogic {
	
	public String applyPermission(String params) {
		String setStore = Config.getString("applyPermission");
		ApplyPermissionBean applyPermissionBean =  JSON.parseObject(params, ApplyPermissionBean.class);
		return HttpUtil.httpRest(TEACH_SERVER, setStore, null, applyPermissionBean, "POST");
	}

	/**
	 * 提交基础测试
	 */
	public String passTest(String params) {
		String path = Config.getString("passTest");
		PassTestBean passTestBean =  JSON.parseObject(params, PassTestBean.class);
		return HttpUtil.httpRest(TEACH_SERVER, path, null, passTestBean, "POST");

	}

	public String insertanswerpermission(String params) {
		String updateanswerpermission = Config.getString("insertanswerpermission");
		UpdateanswerpermissionBean updateanswerpermissionBean =  JSON.parseObject(
				params, UpdateanswerpermissionBean.class);
		return HttpUtil.httpRest(TEACH_SERVER, updateanswerpermission,
				null, updateanswerpermissionBean, "POST");

	}

	public String applyInterviewTime(String params) {
		String applyinterviewtime = Config.getString("applyinterviewtime");
		ApplyinterviewtimeBean applyinterviewtimeBean =  JSON.parseObject(params, ApplyinterviewtimeBean.class);
		return HttpUtil.httpRest(TEACH_SERVER, applyinterviewtime, null, applyinterviewtimeBean, "POST");

	}

	public String applyFacingTeachTime(String params) {
		String applyfacingteachtime = Config.getString("applyfacingteachtime");
		ApplyfacingteachtimeBean applyfacingteachtimeBean =  JSON.parseObject(params, ApplyfacingteachtimeBean.class);
		return HttpUtil.httpRest(TEACH_SERVER, applyfacingteachtime, null, applyfacingteachtimeBean, "POST");
	}
	
	public String diagnoseAnswerSubmit(String params){
		String diagnoseAnswerSubmit = Config.getString("diagnoseAnswerSubmit");
		DiagnoseAnswerSubmitBean diagnoseAnswerSubmitBean=  JSON.parseObject(params, DiagnoseAnswerSubmitBean.class);
		return HttpUtil.httpRest(TEACH_SERVER, diagnoseAnswerSubmit, null, diagnoseAnswerSubmitBean, "POST");
	}
	
}
