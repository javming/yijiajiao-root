package com.yijiajiao.root.bean.command;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-09-11:26
 */
public class AddCouponBean {
    private int type;//0:平台优惠券 1：工作室优惠券
    private String name;// 代金券名称
    private double value;// 优惠券面值
    private int amount;// 开通数量
    private int usageRange; //适用类型0：工作室全部课程1：工作室指定课程
    private String waresids;// 优惠券指定课程的id多个课程以"_"隔开
    private int usageCondition;// 使用条件0：无条件 1：满足最低使用价格
    private double minPrice; //最低使用价格
    private String startDate;// 开始时间
    private String endDate;// 结束时间
    private String teacherOpenid;// 工作室教师openid
    private String description;// 描述

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUsageRange() {
        return usageRange;
    }

    public void setUsageRange(int usageRange) {
        this.usageRange = usageRange;
    }

    public String getWaresids() {
        return waresids;
    }

    public void setWaresids(String waresids) {
        this.waresids = waresids;
    }

    public int getUsageCondition() {
        return usageCondition;
    }

    public void setUsageCondition(int usageCondition) {
        this.usageCondition = usageCondition;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTeacherOpenid() {
        return teacherOpenid;
    }

    public void setTeacherOpenid(String teacherOpenid) {
        this.teacherOpenid = teacherOpenid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
