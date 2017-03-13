package com.yijiajiao.root.command.service;

import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.AddActivityBean;
import com.yijiajiao.root.bean.command.AddCouponBean;
import com.yijiajiao.root.bean.command.UpdateActivityBean;
import com.yijiajiao.root.bean.command.UpdateCouponBean;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.PROMOTION_SERVER;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-09-17:36
 */
@Service("promotionLogic")
public class PromotionLogic {


    public String addCoupon(String params) {
        String path = Config.getString("addCoupon");
        AddCouponBean addCouponBean = JSON.parseObject(params,AddCouponBean.class);
        return HttpUtil.httpRest(PROMOTION_SERVER, path, null, addCouponBean, "POST");
    }

    public String updateCoupon(String params) {
        String path = Config.getString("updateCoupon");
        UpdateCouponBean updateCouponBean = JSON.parseObject(params, UpdateCouponBean.class);
        return HttpUtil.httpRest(PROMOTION_SERVER, path, null,updateCouponBean, "POST");
    }

    public String addActivity(String params) {
        String path = Config.getString("addActivity");
        AddActivityBean addActivityBean= JSON.parseObject(params,AddActivityBean.class);
        return HttpUtil.httpRest(PROMOTION_SERVER, path, null,addActivityBean, "POST");
    }

    public String updateActivity(String params) {
        String path = Config.getString("updateActivity");
        UpdateActivityBean updateActivityBean = JSON.parseObject(params,UpdateActivityBean.class);
        return HttpUtil.httpRest(PROMOTION_SERVER, path, null,updateActivityBean, "POST");
    }
}
