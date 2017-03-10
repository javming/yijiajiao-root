package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.BindAliPayBean;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

@Service("financeLogic")
public class FinanceLogic extends BasicLogic{


	public String bindAliPay(String params) {
		String bindAliPay = Config.getString("bindAliPay");
		BindAliPayBean bindAliPayBean = JSON.parseObject(params, BindAliPayBean.class);
		return RabbitmqUtil.httpRest(finance_server, bindAliPay, null, bindAliPayBean, "POST");
	}
}
