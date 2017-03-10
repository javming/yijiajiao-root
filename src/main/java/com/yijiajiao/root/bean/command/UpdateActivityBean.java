package com.yijiajiao.root.bean.command;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-09-17:09
 */
public class UpdateActivityBean {
    private int id;// 活动id
    private String endDate;// 结束时间
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
