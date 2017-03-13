package com.yijiajiao.root.command.service;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.*;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.SALE_SERVER;


@Service("saleLogic")
public class SaleLogic {
	
	public String updateAppraise(String params) {
		String updateAppraise = Config.getString("updateAppraise");
		UpdateAppraiseBean updateAppraiseBean = JSON.parseObject(params, UpdateAppraiseBean.class);
		return HttpUtil.httpRest(SALE_SERVER, updateAppraise, null, updateAppraiseBean, "POST");

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
		return HttpUtil.httpRest(SALE_SERVER, createOrder, null, param, "POST");
	}

	public String updateAppraiseReback(String params) {
		String updateAppraise = Config.getString("updateAppraiseReback");
		UpdateAppraiseRebackBean updateAppraiseRebackBean = JSON.parseObject(params, UpdateAppraiseRebackBean.class);
		return HttpUtil.httpRest(SALE_SERVER, updateAppraise, null, updateAppraiseRebackBean, "PUT");
	}

	public String createRefund(String params) {
		String updateAppraise = Config.getString("createRefund");
		CreateRefundBean createRefundBean = JSON.parseObject(params, CreateRefundBean.class);
		return HttpUtil.httpRest(SALE_SERVER, updateAppraise, null, createRefundBean, "POST");
	}
}
