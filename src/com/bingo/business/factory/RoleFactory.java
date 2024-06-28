package com.bingo.business.factory;

import com.bingo.commons.enums.RoleType;
import com.bingo.commons.exception.BingoException;
import com.bingo.commons.pojo.identity.Role;

/**
 * @author nia
 * @description 单例工厂类
 * @Date 2024/6/4
 */
public class RoleFactory implements RoleAbstractFactory{

    //私有空参构造方法
    private RoleFactory(){}

    //静态内部类的方式实现单例模式
    private static class RoleFactoryHolder{
        private static final RoleFactory ROLE_FACTORY = new RoleFactory();
    }
    //外部访问，提供获取实例的方法
    public static RoleFactory getInstance(){
        return RoleFactoryHolder.ROLE_FACTORY;
    }

    /**
     * 创建角色
     * @param roleType 角色对象
     * @return
     * @param <T> 角色泛型
     */
    @Override
    public <T extends Role> T createRole(RoleType roleType) {
        try {
            return (T) roleType.getRoleClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BingoException(e);
        }
    }

}
