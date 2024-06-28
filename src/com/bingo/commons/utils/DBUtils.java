package com.bingo.commons.utils;

import com.bingo.commons.pojo.Activity;
import com.bingo.commons.pojo.Cart;
import com.bingo.commons.pojo.Goods;
import com.bingo.commons.pojo.identity.*;
import com.bingo.data.MyDB;
import com.bingo.data.bpFileTree.Table;
import com.bingo.data.graph.RelaSchema;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.bingo.commons.utils.TransformUtils.dataFormat;

/**
 * @author nia
 * @description
 * @Date 2024/6/25
 */
public class DBUtils {

    private static final String root = "myDB";

    /**
     * 新增管理员对象
     * @param admin
     */
    public static void insertAdmin(PlatformAdmin admin) {
        Table table = getTable("admin");
        table.createIndex(0);
        table.createIndex(1);
        LinkedList<String> list = TransformUtils.admin2LinkedList(admin);
        table.insert(list);
    }

    public static void insertPurchaser(Purchaser user) {
        Table table = getTable("purchaser");
        table.createIndex(0);
        table.createIndex(1);
        LinkedList<String> list = TransformUtils.purchaser2LinkedList(user);
        table.insert(list);
        initCart(user);
    }

    public static void insertMerchant(Merchant user) {
        Table table = getTable("merchant");
        table.createIndex(0);
        table.createIndex(1);
        LinkedList<String> list = TransformUtils.merchant2LinkedList(user);
        table.insert(list);
    }

    public static PlatformAdmin getAdmin(String account){
        Table table = getTable("admin");
        LinkedList<String> linkedList = table.findByIdx(1, account);
        return TransformUtils.list2Admin(linkedList);
    }

    public static Purchaser getPurchaser(String account){
        Table table = getTable("purchaser");
        LinkedList<String> linkedList = table.findByIdx(1, account);
        if (linkedList == null || linkedList.isEmpty()){
            return null;
        }
        return TransformUtils.list2Purchaser(linkedList);
    }

    public static Merchant getMerchant(String account){
        Table table = getTable("merchant");
        LinkedList<String> linkedList = table.findByIdx(1, account);
        if (linkedList == null || linkedList.isEmpty()){
            return null;
        }
        return TransformUtils.list2Merchant(linkedList);
    }

    public static void insertMessages(String message, Purchaser user){
        Table table = getTable("message");
        table.createIndex(0);
        LinkedList<String> list = new LinkedList<>();
        list.add(user.getAccount());
        list.add(message);
        table.insert(list);
    }

    public static List<String> getAdminMessages() {
        Table message = getTable("message");
        List<String> arrayList = new ArrayList<>();
        LinkedList<LinkedList<String>> allByCSV = message.getAllByCSV();
        for (LinkedList<String> list : allByCSV) {
            String s = list.get(0) + ":" + list.get(1);
            arrayList.add(s);
        }
        return arrayList;
    }

    public static List<Merchant> getAllMerchants() {
        Table table = getTable("merchants");
        List<Merchant> arrayList = new ArrayList<>();
        LinkedList<LinkedList<String>> allByCSV = table.getAllByCSV();
        for (LinkedList<String> list : allByCSV) {
            arrayList.add(TransformUtils.list2Merchant(list));
        }
        return arrayList;
    }

    public static List<Purchaser> getAllPurchasers() {
        Table table = getTable("purchaser");
        List<Purchaser> arrayList = new ArrayList<>();
        LinkedList<LinkedList<String>> allByCSV = table.getAllByCSV();
        for (LinkedList<String> list : allByCSV) {
            arrayList.add(TransformUtils.list2Purchaser(list));
        }
        return arrayList;
    }

    public static List<Goods> getAllGoods() {
        Table table = getTable("goods");
        LinkedList<LinkedList<String>> allByCSV = table.getAllByCSV();
        return TransformUtils.list2GoodsList(allByCSV);
    }

    public static List<Activity> getActivities() {
        Table table = getTable("activity");
        List<Activity> arrayList = new ArrayList<>();
        LinkedList<LinkedList<String>> allByCSV = table.getAllByCSV();
        for (LinkedList<String> list : allByCSV) {
            arrayList.add(TransformUtils.list2Activity(list));
        }
        return arrayList;
    }

    private static Table getTable(String name) {
        MyDB myDB = new MyDB(root);
        return myDB.createTable(name);
    }

    private static void initCart(Purchaser purchaser){
        Table cart = getTable("cart");
        cart.createIndex(0);
        LinkedList<String> list = new LinkedList<>();
        list.add(purchaser.getPId());
        cart.insert(list);
    }


    public static void insertGoods2Cart(Goods goods, Purchaser user) {
        Table cart = getTable("cart");
        LinkedList<String> linkedList = cart.findByIdx(0,user.getPId());
        linkedList.add(goods.getgId());
        cart.updateByFristIdx(linkedList);
    }

    public static Cart getCart(String pId) {
        Cart cart = new Cart();
        cart.setUid(pId);

        Table goods = getTable("goods");
        Table table = getTable("cart");
        LinkedList<String> linkedList = table.findByIdx(0, pId);
        if (linkedList == null || linkedList.size() <=1){
            return cart;
        }
        cart.setCount(linkedList.size()-1);
        List<LinkedList<String>> linkedLists = new ArrayList<>();
        for (int i = 1; i < linkedList.size(); i++) {
            String id = linkedList.get(i);
            LinkedList<String> list = goods.findByIdx(0, id);
            linkedLists.add(list);
        }
        List<Goods> goodsList = TransformUtils.list2GoodsList(linkedLists);
        cart.setGoodsList(goodsList);
        cart.calcTotal();
        return cart;
    }

    public static void insertGoods(Goods goods){
        Table table = getTable("goods");
        table.createIndex(0);
        LinkedList<String> list = TransformUtils.goods2List(goods);
        table.insert(list);
    }
    public static void updatePurchaser(Purchaser user) {
        Table purchaser = getTable("purchaser");
        LinkedList<String> list = TransformUtils.purchaser2LinkedList(user);
        purchaser.updateByFristIdx(list);
    }
    public static void rmGoodsFromCart(String pId,String gId) {
        Table goods = getTable("goods");
        LinkedList<String> linkedList = goods.findByIdx(0, pId);
        linkedList.remove(gId);
        goods.updateByFristIdx(linkedList);
    }
    public static String getIncreaseId(String filename){
        String fileName = "myDB\\"+ filename;
        int id = 1;
        try{
            File file = new File(fileName);
            if (file.exists()){
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                id = Integer.parseInt(reader.readLine());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        // 将新的值写回文件
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(Integer.toString(id + 1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataFormat(Integer.toString(id));
    }

    public static Integer getFollowersNum(String pId){
        RelaSchema userFollow = getRelaSchema("userFollower");
        return userFollow.getEdgeNum(pId);
    }

    public static Integer getFollowingsNum(String pId) {
        RelaSchema userFollowing = getRelaSchema("userFollowing");
        return userFollowing.getEdgeNum(pId);
    }

    /**
     * 获取用户关注列表
     */
    public static LinkedList<String> getFollowers(String pId) {
        RelaSchema userFollower = getRelaSchema("userFollower");
        return userFollower.getAllEdge(pId);
    }

    public static LinkedList<String> getFollowings(String pId) {
        RelaSchema userFollower = getRelaSchema("userFollowing");
        return userFollower.getAllEdge(pId);
    }

    public static void insertActivity(Activity activity) {
        Table table = getTable("activity");
        table.createIndex(0);
        LinkedList<String> list = new LinkedList<>();
        list.add(activity.getCode());
        list.add(activity.getName());
        list.add(activity.getDesc());
        table.insert(list);
    }

    public static void insertFollowers(Purchaser purchaser, Purchaser purchaser1,String name) {
        RelaSchema relaSchema = getRelaSchema(name);
        relaSchema.addNode(Integer.parseInt(purchaser.getPId()),purchaser.getAccount());
        relaSchema.addNode(Integer.parseInt(purchaser1.getPId()),purchaser1.getAccount());
        relaSchema.addEdge(purchaser.getAccount(),purchaser1.getAccount());
    }

    public static List<Goods> getGoodsBymid(String mId) {
        Table goods = getTable("goods");
//        goods.findByIdx(0,mId);
        return new ArrayList<>();
    }

    public static RelaSchema getRelaSchema(String name){
        MyDB db = new MyDB(root);
        return db.createRelaSchema(name);
    }
}