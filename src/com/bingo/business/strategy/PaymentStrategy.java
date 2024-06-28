package com.bingo.business.strategy;

/**
 * @author nia
 * @description 支付策略接口
 * @Date 2024/6/4
 */
public interface PaymentStrategy {
    Double calcPrice(Double originalPrice);
}
