package com.bingo.business.account;

import com.bingo.business.Platform;
import com.bingo.commons.enums.ResponseType;
import com.bingo.commons.enums.RoleType;
import com.bingo.commons.pojo.identity.*;
import com.bingo.commons.utils.DBUtils;
import com.bingo.commons.utils.GlobalFormatUtil;
import com.bingo.commons.vo.ResultVO;

/**
 * @author nia
 * @description 登录服务
 * @Date 2024/6/4
 */
public class AccountService {

    //平台对象
    private static Platform platform = Platform.getInstance();

    //登录逻辑
    public static ResultVO<Role> login(String account, String pwd, RoleType roleType){
        Role role;
        ResponseType responseType;
        switch (roleType){
            case PLATFORM_ADMIN -> {
                role = DBUtils.getAdmin(account);
                responseType = ResponseType.ADMIN_LOGIN;
            }
            case MERCHANT ->{
                role = DBUtils.getMerchant(account);
                responseType = ResponseType.MERCHANT_LOGIN;
            }
            case PURCHASER ->{
                role = DBUtils.getPurchaser(account);
                responseType = ResponseType.PURCHASER_LOGIN;
            }
            default -> {
                return ResultVO.fail(account +"登录失败");
            }
        }
        if (pwd.equals(role.getPwd())) {
            return ResultVO.success(responseType.getCode(),"登录成功",role);
        }
        return ResultVO.fail(account +"登录失败");
    }

    //User注册逻辑
    public static  <T extends User> ResultVO<T> registerUser(String account, String pwd,RoleType roleType){
        T user = platform.createRole(roleType);
        if (roleType == RoleType.PURCHASER){
            Purchaser purchaser= (Purchaser) user;
            purchaser.setPId(DBUtils.getIncreaseId("id.txt"));
            purchaser.setAccount(account);
            purchaser.setPwd(pwd);
            purchaser.setBalance(0.0);
            purchaser.setAddresses("无地址");
            purchaser.setCreateTime(GlobalFormatUtil.getNowDateString());

            DBUtils.insertPurchaser(purchaser);
        }else if (roleType == RoleType.MERCHANT){
            Merchant merchant= (Merchant) user;
            merchant.setMId(DBUtils.getIncreaseId("id.txt"));
            merchant.setAccount(account);
            merchant.setPwd(pwd);
            merchant.setIncome(0.0);
            merchant.setFollowers(0);
            merchant.setCreateTime(GlobalFormatUtil.getNowDateString());
            DBUtils.insertMerchant(merchant);
        }

        return ResultVO.success("创建用户成功！" + user);
    }

    //admin注册逻辑
    public static <T extends Admin> ResultVO<T> registerAdmin(String account, String pwd,RoleType roleType){
        PlatformAdmin admin = platform.createRole(roleType);
        admin.setAId(DBUtils.getIncreaseId("id.txt"));
        admin.setAccount(account);
        admin.setPwd(pwd);
        DBUtils.insertAdmin(admin);
        return ResultVO.success("创建用户成功！" + admin);
    }


}
