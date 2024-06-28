package com.bingo.menu;

import com.bingo.business.account.AccountService;
import com.bingo.commons.constants.CollConstants;
import com.bingo.commons.enums.RoleType;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.pojo.identity.User;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;

import java.util.List;

/**
 * @author nia
 * @description 登录界面
 * @Date 2024/6/5
 */
public class Start {

    public static <T extends Role> ResultVO<T> login() {
        List<String> login = CollConstants.LOGIN;
        GlobalFormatUtil.commands(login);
        int inputChar = InputUtil.inputChar(login.size());
        RoleType roleType = null;
        switch (inputChar) {
            case 1 -> roleType = RoleType.PURCHASER;
            case 2 -> roleType = RoleType.MERCHANT;
            case 3 -> roleType = RoleType.PLATFORM_ADMIN;
            case 4 -> {
                return ResultVO.fail();
            }
        }
        String account = InputUtil.inputString("请输入账号：");
        String pwd = InputUtil.inputString("请输入密码：");
        ResultVO<Role> resultVO = AccountService.login(account, pwd, roleType);
        System.out.println(resultVO.getMsg());
        return (ResultVO<T>) resultVO;
    }

    public static void register() {
        List<String> register = CollConstants.REGISTER;
        GlobalFormatUtil.commands(register);
        int inputChar = InputUtil.inputChar(register.size());

        RoleType roleType;

        switch (inputChar) {
            case 1 -> roleType = RoleType.PURCHASER;
            case 2 -> roleType = RoleType.MERCHANT;
            default -> {
                return;
            }
        }

        String account = InputUtil.inputString("请输入用户名：");
        String pwd;
        while (true) {
            pwd = InputUtil.inputString("请输入密码：");
            String confirm = InputUtil.inputString("请确认密码：");
            if (pwd.equals(confirm)) {
                break;
            }
            System.out.println("两次输入的密码不同，请重新输入");
        }

        ResultVO<User> resultVO = AccountService.registerUser(account, pwd, roleType);
        System.out.println(resultVO.getMsg());
    }

}
