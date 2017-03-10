package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.FeedBackBean;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

@Service("ossLogic")
public class OssLogic extends BasicLogic{

	public String feedBack(String params) {
		String feedBack = Config.getString("feedBack");
		FeedBackBean feedBackBean = JSON.parseObject(params, FeedBackBean.class);
		return RabbitmqUtil.httpRest(oss_server, feedBack, null, feedBackBean, "POST");
	}
}
