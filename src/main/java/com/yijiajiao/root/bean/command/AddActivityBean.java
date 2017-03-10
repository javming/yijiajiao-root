package com.yijiajiao.root.bean.command;

/**
 * @AUTHOR zhaoming@eduspace
 * @CREATE 2017-01-09-16:52
 */
public class AddActivityBean {
    private String teacherOpenid;
    private String name; //活动名称
    private String isExclude;// 是否排斥 Y：是N：否,用来判断多个促销是否同时可用
    private String priority; //优先级，多个促销活动时使用
    private String startDate; // 开始时间
    private String endDate;// 结束时间
    private String waresids;// 活动指定课程的id多个以"_"隔开
    private String description;// 描述
    private String value;// 折扣值

    public String getTeacherOpenid() {
        return teacherOpenid;
    }

    public void setTeacherOpenid(String teacherOpenid) {
        this.teacherOpenid = teacherOpenid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsExclude() {
        return isExclude;
    }

    public void setIsExclude(String isExclude) {
        this.isExclude = isExclude;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public String getWaresids() {
        return waresids;
    }

    public void setWaresids(String waresids) {
        this.waresids = waresids;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
