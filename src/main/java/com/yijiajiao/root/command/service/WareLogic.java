package com.yijiajiao.root.command.service;


import com.alibaba.fastjson.JSON;
import com.yijiajiao.root.bean.command.*;
import com.yijiajiao.root.utils.Config;
import com.yijiajiao.root.utils.HttpUtil;
import org.springframework.stereotype.Service;

import static com.yijiajiao.root.utils.RootUtil.WARES_SERVER;

/**
 * 商品资源方向
 */

@Service("wareLogic")
public class WareLogic {


	/**
	 * 上传视频
	 */
	public String uploadVideo(String params) {
		String path = Config.getString("uploadvideo");
		UploadVideoParamBean uploadVideoParamBean = JSON.parseObject(params, UploadVideoParamBean.class);
		return HttpUtil.httpRest(WARES_SERVER, path, null, uploadVideoParamBean, "POST");
	}

	/**
	 * 创建直播课
	 */
	public String wareLive(String params) {
		String path = Config.getString("warelive");
		WareLiveBean wareLiveBean = JSON.parseObject(params, WareLiveBean.class);
		return HttpUtil.httpRest(WARES_SERVER, path, null, wareLiveBean, "POST");
	}

	/**
	 * 创建视频课
	 */

	public String wareVideo(String params) {
		String path = Config.getString("warevideo");
		WareVideoBean wareVideoBean = JSON.parseObject(params, WareVideoBean.class);
		return HttpUtil.httpRest(WARES_SERVER, path, null, wareVideoBean, "POST");
	}

	/**
	 * 创建一对一课
	 */
	public String wareOne2One(String params) {
		String path = Config.getString("wareOne2One");
		WareOne2OneBean wareOne2OneBean = JSON.parseObject(params, WareOne2OneBean.class);
		return HttpUtil.httpRest(WARES_SERVER, path, null, wareOne2OneBean, "POST");
	}

	/**
	 * 交卷
	 */
	public String commitExam(String params) {
		String path = Config.getString("commitExam");
		CommitExamBean commitExamBean = JSON.parseObject(params, CommitExamBean.class);
		return HttpUtil.httpRest(WARES_SERVER, path, null, commitExamBean, "POST");
	}

	/**
	 * 修改课程
	 */
	public String updateWaresLive(String params) {
		String path = Config.getString("updateWaresLive");
		WareLiveBean wareLiveBean = JSON.parseObject(params,WareLiveBean.class);
		return HttpUtil.httpRest(WARES_SERVER,path,null,wareLiveBean,"PUT");
	}
}
