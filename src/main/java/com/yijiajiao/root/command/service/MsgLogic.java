package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.SetMsgBean;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

@Service("msgLogic")
public class MsgLogic extends BasicLogic{

	public String SetMsg(String params) {
		String setMsg = Config.getString("setMsg");
		SetMsgBean setMsgBean = JSON.parseObject(params, SetMsgBean.class);
		return RabbitmqUtil.httpRest(msg_server, setMsg, null, setMsgBean, "POST");
	}
}
