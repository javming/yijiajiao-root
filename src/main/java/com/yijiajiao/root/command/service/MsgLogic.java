package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.SetMsgBean;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.MSG_SERVER;

@Service("msgLogic")
public class MsgLogic{

	public String SetMsg(String params) {
		String setMsg = Config.getString("setMsg");
		SetMsgBean setMsgBean = JSON.parseObject(params, SetMsgBean.class);
		return HttpUtil.httpRest(MSG_SERVER, setMsg, null, setMsgBean, "POST");
	}
}
