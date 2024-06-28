package com.bingo.menu.home;

import com.bingo.commons.constants.CollConstants;
import com.bingo.commons.pojo.Activity;
import com.bingo.commons.pojo.Goods;
import com.bingo.commons.pojo.identity.Admin;
import com.bingo.commons.pojo.identity.Merchant;
import com.bingo.commons.pojo.identity.Purchaser;
import com.bingo.commons.utils.DBUtils;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.List;
import java.util.Objects;

/**
 * @author nia
 * @description 管理员主界面
 * @Date 2024/6/6
 */
public class AdminHome {
    public static ResultVO<Admin> adminHome(Admin admin) {
        List<String> adminCommands = CollConstants.ADMIN;
        GlobalFormatUtil.roleWelcome("管理员");
        while (true) {
            GlobalFormatUtil.commands(adminCommands);
            int inputChar = InputUtil.inputChar(adminCommands.size());

            switch (inputChar) {
                case 1 -> manageUser();
                case 2 -> manageMerchant();
                case 3 -> manageGoods();
                case 4 -> manageActivities();
                case 5 -> messages();
                case 6 -> {
                    return ResultVO.success("管理员退出登录");
                }
            }
        }

    }



    /**
     * 活动管理
     */
    private static void manageActivities() {
        List<Activity> activities = DBUtils.getActivities();
        if(Objects.isNull(activities)){
            System.out.println("暂无活动");
            return;
        }
        activities.forEach(activity -> System.out.println(activity.toString()));

        if ("y".equalsIgnoreCase(InputUtil.inputString("是否新增活动(y)"))){
            String name = InputUtil.inputString("请输入新增的活动名称：");
            String desc = InputUtil.inputString("请输入活动描述：");
            Activity activity = new Activity(name,desc);
            DBUtils.insertActivity(activity);
            System.out.println("新增活动成功");
        }
    }

    /**
     * 商品管理
     */
    private static void manageGoods() {
        List<Goods> allGoods = DBUtils.getAllGoods();
        if (Objects.isNull(allGoods)){
            System.out.println("暂无商品");
            return;
        }
        allGoods.forEach(good -> System.out.println(good.toString()));
    }

    /**
     * 商家管理
     */
    private static void manageMerchant() {
        List<Merchant> users = DBUtils.getAllMerchants();
        if (Objects.isNull(users)){
            System.out.println("暂无商家");
            return;
        }
        users.forEach(user -> System.out.println(user.toString()));
    }

    /**
     * 用户管理
     */
    private static void manageUser() {
        List<Purchaser> users = DBUtils.getAllPurchasers();
        if (Objects.isNull(users)){
            System.out.println("暂无用户");
            return;
        }
        users.forEach(user -> System.out.println(user.toString()));
    }

    /**
     * 信息
     */
    private static void messages() {
        List<String> messages = DBUtils.getAdminMessages();
        if (Objects.isNull(messages)){
            System.out.println("暂无消息");
            return;
        }
        messages.forEach(System.out::println);
    }

}
