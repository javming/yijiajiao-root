package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.FeedBackBean;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.OSS_SERVER;

@Service("ossLogic")
public class OssLogic{

	public String feedBack(String params) {
	    String feedBack = Config.getString("feedBack");
		FeedBackBean feedBackBean = JSON.parseObject(params, FeedBackBean.class);
		return HttpUtil.httpRest(OSS_SERVER, feedBack, null, feedBackBean, "POST");
	}
}
