package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.ApplyTeacherBean;
import com.yijiajiao.root.bean.command.CompleteInfoBean;
import com.yijiajiao.root.bean.command.SetStoreBean;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.USER_SERVER;

/**
 * 用户管理方向
 * 
 * @author ruyage
 * 
 */
@Service("userLogic")
public class UserLogic {

	public String applyteacher(String params) {
		String applyteacher = Config.getString("applyteacher");
		ApplyTeacherBean applyTeacherBean = JSON.parseObject(params, ApplyTeacherBean.class);
		return HttpUtil.httpRest(USER_SERVER, applyteacher, null, applyTeacherBean, "POST");
	}

	public String setStore(String params) {
		String setStore = Config.getString("setStore");
		SetStoreBean setStoreBean = JSON.parseObject(params, SetStoreBean.class);
		return HttpUtil.httpRest(USER_SERVER, setStore, null, setStoreBean, "POST");
	}

	/**
	 * 完善个人信息
	 */
	public String complete(String params) {
		String path = Config.getString("complete");
		CompleteInfoBean completeInfoBean = JSON.parseObject(params, CompleteInfoBean.class);
		return HttpUtil.httpRest(USER_SERVER, path, null, completeInfoBean, "POST");
	}

}
