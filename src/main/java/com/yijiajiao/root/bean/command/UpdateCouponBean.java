package com.yijiajiao.root.bean.command;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-09-11:58
 */
public class UpdateCouponBean {
    private int id; //代金券id
    private int addAmount; //增加开通的数量
    private String endDate; //代金券结束时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(int addAmount) {
        this.addAmount = addAmount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
