package com.bingo.commons.utils;

import com.bingo.commons.pojo.Activity;
import com.bingo.commons.pojo.Goods;
import com.bingo.commons.pojo.identity.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @author nia
 * @description
 * @Date 2024/6/26
 */
public class TransformUtils {
    /**
     * 转换为角色对象
     */
    public static PlatformAdmin list2Admin(LinkedList<String> list){
       PlatformAdmin platformAdmin = new PlatformAdmin();

       platformAdmin.setAId(list.get(0));
       platformAdmin.setAccount(list.get(1));
       platformAdmin.setPwd(list.get(2));
       platformAdmin.setCreateTime(list.get(3));

       return platformAdmin;
    }
    public static Purchaser list2Purchaser(LinkedList<String> list){
        //pid account pwd balance address createTime
        Purchaser purchaser = new Purchaser();

        purchaser.setPId(list.get(0));
        purchaser.setAccount(list.get(1));
        purchaser.setPwd(list.get(2));
        purchaser.setBalance(Double.parseDouble(list.get(3)));
        purchaser.setAddresses(list.get(4));
        purchaser.setCreateTime(list.get(5));


        return purchaser;
    }
    public static Merchant list2Merchant(LinkedList<String> list){
        //mid account pwd income followers createTime
        Merchant merchant = new Merchant();
        merchant.setMId(list.get(0));
        merchant.setAccount(list.get(1));
        merchant.setPwd(list.get(2));
        merchant.setIncome(Double.parseDouble(list.get(3)));
        merchant.setFollowers(Integer.parseInt(list.get(4)));
        merchant.setCreateTime(list.get(5));
        return merchant;
    }
    /**
     * 角色对象转化为list
     */
    public static LinkedList<String> admin2LinkedList(PlatformAdmin admin) {
        LinkedList<String> list = new LinkedList<>();
        String dataFormat = dataFormat(admin.getAId());

        list.add(dataFormat);
        list.add(admin.getAccount());
        list.add(admin.getPwd());
        list.add(admin.getCreateTime());

        return list;
    }
    public static LinkedList<String> purchaser2LinkedList(Purchaser purchaser) {
        LinkedList<String> list = new LinkedList<>();
        String dataFormat = dataFormat(purchaser.getPId());

        //pid account pwd balance address createTime
        list.add(dataFormat);
        list.add(purchaser.getAccount());
        list.add(purchaser.getPwd());
        list.add(purchaser.getBalance().toString());
        list.add(purchaser.getAddresses());

        list.add(purchaser.getCreateTime());

        return list;
    }
    public static LinkedList<String> merchant2LinkedList(Merchant merchant) {
        //mid account pwd income followers createTime
        LinkedList<String> list = new LinkedList<>();
        String dataFormat = dataFormat(merchant.getMId());

        list.add(dataFormat);
        list.add(merchant.getAccount());
        list.add(merchant.getPwd());
        list.add(merchant.getIncome().toString());
        list.add(merchant.getFollowers().toString());
        list.add(merchant.getCreateTime());

        return list;
    }
    public static String dataFormat(String data){
        if(data.compareTo("0") > 0 && data.compareTo("999") < 0 ){
            data=String.format("%03d", Integer.parseInt(data));
        }
        return data;
    }
    public static LinkedList<String> goods2List(Goods goods) {
        LinkedList<String> list = new LinkedList<>();
        list.add(goods.getgId());
        list.add(goods.getsId());
        list.add(goods.getgName());
        list.add(goods.getPrice().toString());
        return list;
    }
    public static List<Goods> list2GoodsList(List<LinkedList<String>> goodsLinkedLists){
        List<Goods> goodsList = new ArrayList<>();
        for (LinkedList<String> goodsLinkedList : goodsLinkedLists) {
            Goods goods = list2Goods(goodsLinkedList);
            goodsList.add(goods);
        }
        return goodsList;
    }
    public static Goods list2Goods(LinkedList<String> list){
        return new Goods(list.get(0),list.get(1),list.get(2),Double.parseDouble(list.get(3)));
    }
    public static Activity list2Activity(LinkedList<String> list) {
        return new Activity(list.get(0),list.get(1),list.get(2));
    }
}
