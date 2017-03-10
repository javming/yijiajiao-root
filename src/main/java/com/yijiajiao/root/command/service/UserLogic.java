package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.ApplyTeacherBean;
import com.yijiajiao.rabbitmq.bean.CompleteInfoBean;
import com.yijiajiao.rabbitmq.bean.SetStoreBean;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

/**
 * 用户管理方向
 * 
 * @author ruyage
 * 
 */
@Service("userLogic")
public class UserLogic extends BasicLogic{

	public String applyteacher(String params) {
		String applyteacher = Config.getString("applyteacher");
		ApplyTeacherBean applyTeacherBean = JSON.parseObject(params, ApplyTeacherBean.class);
		return RabbitmqUtil.httpRest(user_server, applyteacher, null, applyTeacherBean, "POST");
	}

	public String setStore(String params) {
		String setStore = Config.getString("setStore");
		SetStoreBean setStoreBean = JSON.parseObject(params, SetStoreBean.class);
		return RabbitmqUtil.httpRest(user_server, setStore, null, setStoreBean, "POST");
	}

	/**
	 * 完善个人信息
	 */
	public String complete(String params) {
		String path = Config.getString("complete");
		CompleteInfoBean completeInfoBean = JSON.parseObject(params, CompleteInfoBean.class);
		return RabbitmqUtil.httpRest(user_server, path, null, completeInfoBean, "POST");
	}

}
