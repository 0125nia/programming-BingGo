package com.bingo.business.strategy;

/**
 * @author nia
 * @description 默认支付策略
 * @Date 2024/6/4
 */
public class DefaultPaymentStrategy implements PaymentStrategy{
    @Override
    public Double calcPrice(Double originalPrice) {
        return originalPrice;
    }
}
