package com.yijiajiao.root.bean.command;

public class SlaveBean {
	private Integer id;
	private Integer seq;
	private String startTime;
	private String endTime;
	private String content;
	private String homework;
	private String yjjCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

	public String getYjjCode() {
		return yjjCode;
	}

	public void setYjjCode(String yjjCode) {
		this.yjjCode = yjjCode;
	}
}
