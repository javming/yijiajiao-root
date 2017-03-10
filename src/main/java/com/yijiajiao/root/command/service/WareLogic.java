package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.rabbitmq.bean.*;
import com.yijiajiao.rabbitmq.util.Config;
import com.yijiajiao.rabbitmq.util.RabbitmqUtil;
import org.springframework.stereotype.Service;

/**
 * 商品资源方向
 */

@Service("wareLogic")
public class WareLogic extends BasicLogic{


	/**
	 * 上传视频
	 */
	public String uploadVideo(String params) {
		String path = Config.getString("uploadvideo");
		UploadVideoParamBean uploadVideoParamBean = JSON.parseObject(params, UploadVideoParamBean.class);
		return RabbitmqUtil.httpRest(wares_server, path, null, uploadVideoParamBean, "POST");
	}

	/**
	 * 创建直播课
	 */
	public String wareLive(String params) {
		String path = Config.getString("warelive");
		WareLiveBean wareLiveBean = JSON.parseObject(params, WareLiveBean.class);
		return RabbitmqUtil.httpRest(wares_server, path, null, wareLiveBean, "POST");
	}

	/**
	 * 创建视频课
	 */

	public String wareVideo(String params) {
		String path = Config.getString("warevideo");
		WareVideoBean wareVideoBean = JSON.parseObject(params, WareVideoBean.class);
		return RabbitmqUtil.httpRest(wares_server, path, null, wareVideoBean, "POST");
	}

	/**
	 * 创建一对一课
	 */
	public String wareOne2One(String params) {
		String path = Config.getString("wareOne2One");
		WareOne2OneBean wareOne2OneBean = JSON.parseObject(params, WareOne2OneBean.class);
		return RabbitmqUtil.httpRest(wares_server, path, null, wareOne2OneBean, "POST");
	}

	/**
	 * 交卷
	 */
	public String commitExam(String params) {
		String path = Config.getString("commitExam");
		CommitExamBean commitExamBean = JSON.parseObject(params, CommitExamBean.class);
		return RabbitmqUtil.httpRest(wares_server, path, null, commitExamBean, "POST");
	}

	/**
	 * 修改课程
	 */
	public String updateWaresLive(String params) {
		String path = Config.getString("updateWaresLive");
		WareLiveBean wareLiveBean = JSON.parseObject(params,WareLiveBean.class);
		return RabbitmqUtil.httpRest(wares_server,path,null,wareLiveBean,"PUT");
	}
}
