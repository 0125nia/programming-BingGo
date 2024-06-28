package com.bingo.business.factory;

import com.bingo.commons.enums.RoleType;
import com.bingo.commons.pojo.identity.Role;

/**
 * @author nia
 * @description 工厂接口
 * @Date 2024/6/4
 */
public interface RoleAbstractFactory {
    <T extends Role> T createRole(RoleType roleType);

}
