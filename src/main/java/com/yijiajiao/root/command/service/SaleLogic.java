package com.yijiajiao.root.command.service;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.*;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.rabbitmq.util.RabbitmqUtil.httpRest;

@Service("saleLogic")
public class SaleLogic extends BasicLogic{
	
	public String updateAppraise(String params) {
		String updateAppraise = Config.getString("updateAppraise");
		UpdateAppraiseBean updateAppraiseBean = JSON.parseObject(params, UpdateAppraiseBean.class);
		return httpRest(sale_server, updateAppraise, null, updateAppraiseBean, "POST");

	}

	/**
	 * 提交订单
	 */
	public String createOrder(String params) {
		String createOrder = Config.getString("createOrder");
		CreateOrderBean createOrderBean = JSON.parseObject(params, CreateOrderBean.class);
		Object param;
		if (createOrderBean.getSlaves() == null) {
			CreatevOrderBean v = new CreatevOrderBean();
			v.setCommodity_id(createOrderBean.getCommodity_id());
			v.setOpen_id(createOrderBean.getOpen_id());
			v.setOrder_price(createOrderBean.getOrder_price());
			v.setCommodityType(createOrderBean.getCommodityType());
			v.setDiagnosisGoodsDetailCode(createOrderBean.getDiagnosisGoodsDetailCode());
			v.setDiagnosisGoodsCode(createOrderBean.getDiagnosisGoodsCode());
			v.setDiagnosticRecordsName(createOrderBean.getDiagnosticRecordsName());
			v.setMultiPaperCode(createOrderBean.getMultiPaperCode());
			v.setUsed(createOrderBean.getUsed());
			v.setDiscountPrice(createOrderBean.getDiscountPrice());
			v.setExamStartDate(createOrderBean.getExamStartDate());
			v.setExamEndDate(createOrderBean.getExamEndDate());
			v.setDiscountYard(createOrderBean.getDiscountYard());
			v.setPrice(createOrderBean.getPrice());
			v.setSunshine(createOrderBean.getSunshine());
			v.setTeacherId(createOrderBean.getTeacherId());
			param = v;
		} else {
			param = createOrderBean;
		}
		return httpRest(sale_server, createOrder, null, param, "POST");
	}

	public String updateAppraiseReback(String params) {
		String updateAppraise = Config.getString("updateAppraiseReback");
		UpdateAppraiseRebackBean updateAppraiseRebackBean = JSON.parseObject(params, UpdateAppraiseRebackBean.class);
		return RabbitmqUtil.httpRest(sale_server, updateAppraise, null, updateAppraiseRebackBean, "PUT");
	}

	public String createRefund(String params) {
		String updateAppraise = Config.getString("createRefund");
		CreateRefundBean createRefundBean = JSON.parseObject(params, CreateRefundBean.class);
		return httpRest(sale_server, updateAppraise, null, createRefundBean, "POST");
	}
}
