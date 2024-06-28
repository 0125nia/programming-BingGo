package com.bingo.commons.enums;

import com.bingo.business.strategy.DefaultPaymentStrategy;
import com.bingo.business.strategy.PaymentStrategy;

/**
 * @author nia
 * @description
 * @Date 2024/6/26
 */
public enum ActivityType {

    DEFAULT("1","默认活动", DefaultPaymentStrategy.class),

    ;

    public final String code;

    public final String msg;

    public final Class<? extends PaymentStrategy> aClass;

    ActivityType(String code, String msg, Class<? extends PaymentStrategy> aClass) {
        this.code = code;
        this.msg = msg;
        this.aClass = aClass;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Class<? extends PaymentStrategy> getaClass() {
        return aClass;
    }
}
