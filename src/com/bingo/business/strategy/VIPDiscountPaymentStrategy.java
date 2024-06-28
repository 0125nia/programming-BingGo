package com.bingo.business.strategy;

/**
 * @author nia
 * @description VIP用户付款策略
 * @Date 2024/6/5
 */
public class VIPDiscountPaymentStrategy implements PaymentStrategy{
    @Override
    public Double calcPrice(Double originalPrice) {
        return originalPrice * 0.8;
    }
}
