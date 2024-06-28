package com.bingo.business.strategy;

/**
 * @author nia
 * @description 满减优惠付款策略
 * @Date 2024/6/5
 */
public class FixedAmountDiscountPaymentStrategy implements PaymentStrategy{

    //满足金额
    private final Double fixedAmount;
    //折扣金额
    private final Double discount;

    public FixedAmountDiscountPaymentStrategy(Double fixedAmount, Double discount) {
        this.fixedAmount = fixedAmount;
        this.discount = discount;
    }

    @Override
    public Double calcPrice(Double originalPrice) {
        double discountPrice = discount * (originalPrice % fixedAmount);
        return originalPrice - discountPrice;
    }
}
