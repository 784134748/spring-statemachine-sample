package org.apframework.statemachine.core.enums;


/**
 * @author yalonglee
 */
public enum TradeEvents {

    /**
     * 超时未支付
     */
    PAY_TIMEOUT,
    /**
     * 支付成功
     */
    PAY_SUCCEED,
    /**
     * 确认接单
     */
    CONFIRM,
    /**
     * 拒绝接单
     */
    REJECT,
    /**
     * 买家提货
     */
    DELIVERY,
    /**
     * 确认收货
     */
    RECEIVE,
    /**
     * 同意退款
     */
    REFUND,
    /**
     * 取消订单
     */
    CLOSE,
    /**
     * 超时处理
     */
    TIMEOUT

}
