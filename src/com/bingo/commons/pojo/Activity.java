package com.bingo.commons.pojo;

/**
 * @author nia
 * @description 活动
 * @Date 2024/6/4
 */
public class Activity {

    private String code;

    private String name;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Activity(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
    public Activity(String name, String desc) {
        this.code = "1";
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "活动名称:" + getName() + ", 详情:" + getDesc();
    }
}
