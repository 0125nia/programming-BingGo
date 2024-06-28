package com.bingo.business.strategy;

/**
 * @author nia
 * @description 策略类上下文
 * @Date 2024/6/4
 */
public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    /**
     * 配置优惠策略
     * @param paymentStrategy
     */
    public PaymentContext(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * 价格优惠处理
     * @param price
     * @return
     */
    public Double paymentProcess(Double price){
        return paymentStrategy.calcPrice(price);
    }
}
