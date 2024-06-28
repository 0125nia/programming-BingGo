package com.bingo.commons.pojo.identity;

/**
 * @author nia
 * @description 用户接口
 * @Date 2024/6/4
 */
public abstract class User extends Role{
    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}