package com.bingo.menu;

import com.bingo.commons.constants.CollConstants;
import com.bingo.commons.pojo.identity.Role;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.utils.InputUtil;
import com.bingo.commons.vo.ResultVO;
import com.bingo.menu.command.Command;
import com.bingo.menu.command.CommandContext;

import java.util.List;

/**
 * @author nia
 * @description 面板
 * @Date 2024/6/5
 */
public class Panel {

    /**
     * 开始界面
     * @return
     */
    public static void start(){
        List<String> start = CollConstants.START;

        while (true) {
            GlobalFormatUtil.logo();
            GlobalFormatUtil.startWelcome();
            GlobalFormatUtil.commands(start);
            int inputChar = InputUtil.inputChar2(0,start.size());

            switch (inputChar){
                case 1 -> {
                    ResultVO<Role> resultVO = Start.login();
                    if (resultVO.getData() != null){
                        home(resultVO);
                    }
                }
                case 2 -> Start.register();
                case 3 ->{
                    InputUtil.close();
                    System.exit(1);
                }
            }
        }
    }

    /**
     * 主页面
     * @param resultVO
     */
    public static void home(ResultVO<Role> resultVO){
        if (resultVO == null || resultVO.getData() ==null || resultVO.getCode() ==null){
            System.out.println("登录失败！");
            return;
        }
        String code = resultVO.getCode();
        Command command = CommandContext.getCommand(code);
        command.execute(resultVO.getData());
    }
}
