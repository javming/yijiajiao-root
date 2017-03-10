package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.*;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

@Service("teachLogic")
public class TeachLogic extends BasicLogic{
	
	public String applyPermission(String params) {
		String setStore = Config.getString("applyPermission");
		ApplyPermissionBean applyPermissionBean =  JSON.parseObject(params, ApplyPermissionBean.class);
		return RabbitmqUtil.httpRest(teach_server, setStore, null, applyPermissionBean, "POST");
	}

	/**
	 * 提交基础测试
	 */
	public String passTest(String params) {
		String path = Config.getString("passTest");
		PassTestBean passTestBean =  JSON.parseObject(params, PassTestBean.class);
		return RabbitmqUtil.httpRest(teach_server, path, null, passTestBean, "POST");

	}

	public String insertanswerpermission(String params) {
		String updateanswerpermission = Config.getString("insertanswerpermission");
		UpdateanswerpermissionBean updateanswerpermissionBean =  JSON.parseObject(
				params, UpdateanswerpermissionBean.class);
		return RabbitmqUtil.httpRest(teach_server, updateanswerpermission,
				null, updateanswerpermissionBean, "POST");

	}

	public String applyInterviewTime(String params) {
		String applyinterviewtime = Config.getString("applyinterviewtime");
		ApplyinterviewtimeBean applyinterviewtimeBean =  JSON.parseObject(params, ApplyinterviewtimeBean.class);
		return RabbitmqUtil.httpRest(teach_server, applyinterviewtime, null, applyinterviewtimeBean, "POST");

	}

	public String applyFacingTeachTime(String params) {
		String applyfacingteachtime = Config.getString("applyfacingteachtime");
		ApplyfacingteachtimeBean applyfacingteachtimeBean =  JSON.parseObject(params, ApplyfacingteachtimeBean.class);
		return RabbitmqUtil.httpRest(teach_server, applyfacingteachtime, null, applyfacingteachtimeBean, "POST");
	}
	
	public String diagnoseAnswerSubmit(String params){
		String diagnoseAnswerSubmit = Config.getString("diagnoseAnswerSubmit");
		DiagnoseAnswerSubmitBean diagnoseAnswerSubmitBean=  JSON.parseObject(params, DiagnoseAnswerSubmitBean.class);
		return RabbitmqUtil.httpRest(teach_server, diagnoseAnswerSubmit, null, diagnoseAnswerSubmitBean, "POST");
	}
	
}
