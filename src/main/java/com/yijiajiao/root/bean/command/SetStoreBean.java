package com.yijiajiao.root.bean.command;

import java.util.List;

public class SetStoreBean {

	private String storeName;
	private String introduction;
	private String brief;
	private String label;
	private String customLabel;
	private String storeBackPic;
	private String userOpenId;
	private String provinceCode;
	private String cityCode;
	private String areaCode;
	private String storePic;
	private String teachPic;
	private String videoDescription;//视频描述
	private String weibo;
	private String weChat;
	private String qq;
	private String famousTeacherDescription;	//名师风采描述
	private String famousTeacherPic;	//名师风采图片
	private String famousTeacherVideo;	//名师风采视频
	private List<TeachExperience> teachExperience;
	private List<TeachResult> teachResult;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCustomLabel() {
		return customLabel;
	}

	public void setCustomLabel(String customLabel) {
		this.customLabel = customLabel;
	}

	public String getStoreBackPic() {
		return storeBackPic;
	}

	public void setStoreBackPic(String storeBackPic) {
		this.storeBackPic = storeBackPic;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStorePic() {
		return storePic;
	}

	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}

	public String getTeachPic() {
		return teachPic;
	}

	public void setTeachPic(String teachPic) {
		this.teachPic = teachPic;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getFamousTeacherDescription() {
		return famousTeacherDescription;
	}

	public void setFamousTeacherDescription(String famousTeacherDescription) {
		this.famousTeacherDescription = famousTeacherDescription;
	}

	public String getFamousTeacherPic() {
		return famousTeacherPic;
	}

	public void setFamousTeacherPic(String famousTeacherPic) {
		this.famousTeacherPic = famousTeacherPic;
	}

	public String getFamousTeacherVideo() {
		return famousTeacherVideo;
	}

	public void setFamousTeacherVideo(String famousTeacherVideo) {
		this.famousTeacherVideo = famousTeacherVideo;
	}

	public List<TeachExperience> getTeachExperience() {
		return teachExperience;
	}

	public void setTeachExperience(List<TeachExperience> teachExperience) {
		this.teachExperience = teachExperience;
	}

	public List<TeachResult> getTeachResult() {
		return teachResult;
	}

	public void setTeachResult(List<TeachResult> teachResult) {
		this.teachResult = teachResult;
	}
}
