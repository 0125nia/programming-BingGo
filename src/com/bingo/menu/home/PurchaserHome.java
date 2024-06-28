package com.bingo.menu.home;


import com.bingo.business.Platform;
import com.bingo.commons.constants.BingoConstants;
import com.bingo.commons.constants.CollConstants;
import com.bingo.commons.enums.ActivityType;
import com.bingo.commons.pojo.Activity;
import com.bingo.commons.pojo.Cart;
import com.bingo.commons.pojo.Goods;
import com.bingo.commons.pojo.identity.Purchaser;
import com.bingo.commons.utils.DBUtils;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nia
 * @description 普通用户主界面
 * @Date 2024/6/6
 */
public class PurchaserHome {

    public static ResultVO<Purchaser> purchaserHome(Purchaser user) {
        List<String> purchaser = CollConstants.PURCHASER;
        GlobalFormatUtil.roleWelcome(user.getAccount());
        while (true) {
            GlobalFormatUtil.commands(purchaser);
            int inputChar = InputUtil.inputChar(purchaser.size());

            switch (inputChar) {
                case 1 -> viewGoods(user);
                case 2 -> myCart(user);
                case 3 -> interaction(user);
                case 4 -> self(user);
                case 5 -> {
                    return ResultVO.success("用户" + user.getAccount() + "退出登录");
                }
            }
        }

    }

    /**
     * 查看商品界面
     *
     * @param user
     */
    private static void viewGoods(Purchaser user) {
        List<Goods> allGoods = DBUtils.getAllGoods();
        if (allGoods == null || allGoods.isEmpty()) {
            System.out.println("暂无商品");
            return;
        }
        int size = allGoods.size();
        GlobalFormatUtil.showGoods(allGoods, 0, size);
        System.out.println("加入购物车请选择商品前的编号，退出(q)");
        int i = InputUtil.inputChar(size);
        if (i == 0) {
            return;
        }
        Goods goods = allGoods.get(i - 1);

        DBUtils.insertGoods2Cart(goods, user);
        System.out.println("加入购物车成功！");

        String s = InputUtil.inputString("退出请输入q，继续按其它任意键");
        if ("q".equalsIgnoreCase(s)) {
            return;
        }
        viewGoods(user);
    }

    private static void myCart(Purchaser user) {
        Cart cart = DBUtils.getCart(user.getPId());
        List<Goods> goodsList = cart.getGoodsList();
        GlobalFormatUtil.showGoods(goodsList, 0, goodsList.size());
        System.out.println("共" + cart.getCount() + "件商品,总价" + cart.getTotal());
        System.out.println("付款请输入商品前的序号");
        int i = InputUtil.inputChar(goodsList.size());
        if (i == 0) {
            return;
        }
        Goods goods = goodsList.get(i - 1);
        Double balance = user.getBalance();

        System.out.println("您的余额为：" + balance);
        if (goods.getPrice() > balance) {
            System.out.println("余额不足，即将退出...");
            return;
        }
        String s = InputUtil.inputString("是否付款？(Y)");
        if ("y".equalsIgnoreCase(s)) {
            payment(user, goods);
            System.out.println("付款成功！");
        }
    }

    /**
     * 个人界面
     *
     * @param user
     */
    private static void self(Purchaser user) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(BingoConstants.USERNAME, user.getAccount());
        map.put(BingoConstants.BALANCE, user.getBalance());

        map.put(BingoConstants.FOLLOWERS, DBUtils.getFollowersNum(user.getPId()));
        map.put(BingoConstants.FOLLOWING, DBUtils.getFollowingsNum(user.getPId()));
        map.put(BingoConstants.ADDRESS,user.getAddresses());
        GlobalFormatUtil.list(map);
        String s = InputUtil.inputString("1. 充值\n2. 修改地址\n其它任意键则退出");
        if ("1".equals(s)){
            double money = InputUtil.inputDouble("输入您要充值的金额：");
            user.setBalance(user.getBalance() + money);
            DBUtils.updatePurchaser(user);
            System.out.println("充值成功！");
        }else if ("2".equals(s)){
            String addr = InputUtil.inputString("请输入您的地址：");
            user.setAddresses(addr);
            DBUtils.updatePurchaser(user);
            System.out.println("修改地址成功！");
        }


    }

    private static void interaction(Purchaser user) {
        System.out.println("互动中心");
        List<String> interaction = CollConstants.INTERACTION;
        GlobalFormatUtil.commands(interaction);
        int inputChar = InputUtil.inputChar(interaction.size());

        switch (inputChar) {
            case 1 -> viewActivities();
            case 2 -> followers(user);
            case 3 -> followings(user);
            case 4 -> issue(user);

        }
    }

    private static void followings(Purchaser user) {
        List<String> followings = DBUtils.getFollowings(user.getPId());
        if (followings != null && !followings.isEmpty()){
            followings.forEach(System.out::println);
        }
    }

    private static void issue(Purchaser user) {
        String string = InputUtil.inputString("请输入您的反馈");
        DBUtils.insertMessages(string,user);
        System.out.println("已提交成功！");
        String s = InputUtil.inputString("按任意键退出");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void followers(Purchaser user) {
        System.out.println("您的关注列表：");
        List<String> followers = DBUtils.getFollowers(user.getPId());
        if (followers != null && !followers.isEmpty()){
            followers.forEach(System.out::println);
        }
        String s = InputUtil.inputString("是否要新增关注？（确认请按y）");
        if (!"y".equalsIgnoreCase(s)){
            return;
        }
        String name = InputUtil.inputString("请输入您要关注的用户名：");
        Purchaser purchaser = DBUtils.getPurchaser(name);
        if (purchaser == null){
            System.out.println("该用户不存在！即将退出");
        }else {
            DBUtils.insertFollowers(user,purchaser,"userFollower");
            System.out.println("关注" + purchaser.getAccount() +"成功！");
        }
    }

    private static void viewActivities() {
        List<Activity> activities = DBUtils.getActivities();
        if (activities == null ||activities.isEmpty()){
            System.out.println("暂无活动");
            return;
        }
        activities.forEach(System.out::println);
    }

    private static void payment(Purchaser user, Goods goods) {
        Double price = goods.getPrice();
        price = Platform.getInstance().paymentCalc(price, ActivityType.DEFAULT);
        System.out.println("您已使用\"" + ActivityType.DEFAULT.getMsg() + "\"支付方式");
        user.setBalance(user.getBalance() - price);
        DBUtils.updatePurchaser(user);
        DBUtils.rmGoodsFromCart(user.getPId(), goods.getgId());

    }
}
