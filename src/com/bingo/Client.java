package com.bingo;

import com.bingo.business.account.AccountService;
import com.bingo.commons.enums.RoleType;
import com.bingo.commons.pojo.identity.Admin;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.vo.ResultVO;
import com.bingo.menu.Panel;

/**
 * @author nia
 * @description 客户端入口类
 * @Date 2024/6/5
 */
public class Client {

    public static void main(String[] args) {
        while (true){
            Panel.start();
        }
    }
//public static void main(String[] args) {
//    ResultVO<Admin> resultVO = AccountService.registerAdmin("admin", "admin", RoleType.PLATFORM_ADMIN);
//    Admin data = resultVO.getData();
//    System.out.println(data);
//}

}
