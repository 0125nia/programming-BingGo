package com.bingo.commons.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author nia
 * @description 常量列表
 * @Date 2024/6/5
 */
public class CollConstants {
    public static final List<String> START = Arrays.asList("登录账号", "注册账号", "退出");
    public static final List<String> LOGIN = Arrays.asList("普通用户登录", "商家登录", "管理员登录","返回");
    public static final List<String> REGISTER = Arrays.asList("普通用户注册", "商家注册","返回");
    public static final List<String> PURCHASER = Arrays.asList("查看商品", "购物车", "互动中心", "个人界面", "退出登录");
    public static final List<String> MERCHANT = Arrays.asList("上架商品", "活动", "消息", "商家界面", "退出登录");
    public static final List<String> ADMIN = Arrays.asList("用户管理", "商家管理", "商品管理", "活动管理", "消息", "退出登录");
    public static final List<String> INTERACTION = Arrays.asList("查看活动", "关注用户", "关注商家","用户反馈", "退出");

}
