package com.yijiajiao.root.command.service;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.AddActivityBean;
import com.yijiajiao.rabbitmq.bean.AddCouponBean;
import com.yijiajiao.rabbitmq.bean.UpdateActivityBean;
import com.yijiajiao.rabbitmq.bean.UpdateCouponBean;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-09-17:36
 */
@Service("promotionLogic")
public class PromotionLogic extends BasicLogic{


    public String addCoupon(String params) {
        String path = Config.getString("addCoupon");
        AddCouponBean addCouponBean = JSON.parseObject(params,AddCouponBean.class);
        return RabbitmqUtil.httpRest(promotion_server, path, null, addCouponBean, "POST");
    }

    public String updateCoupon(String params) {
        String path = Config.getString("updateCoupon");
        UpdateCouponBean updateCouponBean = JSON.parseObject(params, UpdateCouponBean.class);
        return RabbitmqUtil.httpRest(promotion_server, path, null,updateCouponBean, "POST");
    }

    public String addActivity(String params) {
        String path = Config.getString("addActivity");
        AddActivityBean addActivityBean= JSON.parseObject(params,AddActivityBean.class);
        return RabbitmqUtil.httpRest(promotion_server, path, null,addActivityBean, "POST");
    }

    public String updateActivity(String params) {
        String path = Config.getString("updateActivity");
        UpdateActivityBean updateActivityBean = JSON.parseObject(params,UpdateActivityBean.class);
        return RabbitmqUtil.httpRest(promotion_server, path, null,updateActivityBean, "POST");
    }
}
