package com.bingo.menu.home;

import com.bingo.commons.constants.CollConstants;
import com.bingo.commons.pojo.Activity;
import com.bingo.commons.pojo.identity.Merchant;
import com.bingo.commons.utils.DBUtils;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.List;
import java.util.Objects;


/**
 * @author nia
 * @description 商家主界面
 * @Date 2024/6/6
 */
public class MerchantHome {
    public static ResultVO<Merchant> merchantHome(Merchant user) {
        List<String> merchant = CollConstants.MERCHANT;
        GlobalFormatUtil.roleWelcome(user.getName());
        while (true) {
            GlobalFormatUtil.commands(merchant);
            int inputChar = InputUtil.inputChar(merchant.size());

            switch (inputChar) {
                case 1 -> release(user);
                case 2 -> activity(user);
                case 3 -> messages(user);
                case 4 -> self(user);
                case 5 -> {
                    return ResultVO.success("商家" +user.getName() + "退出登录");
                }
            }
        }

    }

    private static void activity(Merchant user) {
        List<Activity> activities = DBUtils.getActivities();
        if(Objects.isNull(activities)){
            System.out.println("暂无活动");
            return;
        }
        GlobalFormatUtil.objectCommands(activities);
        InputUtil.inputChar(activities.size());
    }

    private static void self(Merchant user) {

    }

    private static void messages(Merchant user) {

    }

    private static void release(Merchant user) {


    }
}
