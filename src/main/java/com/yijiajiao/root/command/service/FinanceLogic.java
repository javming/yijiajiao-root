package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.BindAliPayBean;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.FINANCE_SERVER;

@Service("financeLogic")
public class FinanceLogic{


	public String bindAliPay(String params) {
		String bindAliPay = Config.getString("bindAliPay");
		BindAliPayBean bindAliPayBean = JSON.parseObject(params, BindAliPayBean.class);
		return HttpUtil.httpRest(FINANCE_SERVER, bindAliPay, null, bindAliPayBean, "POST");
	}
}
