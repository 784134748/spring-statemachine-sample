package org.apframework.statemachine.core.enums;


/**
 * @author yalonglee
 */
public enum TradeStates {

    /**
     * 状态机
     */

    /**
     * 待支付
     */
    WAITING_PAY,
    /**
     * 待确认
     */
    WAITING_CONFIRM,
    /**
     * 待发货
     */
    WAITING_DELIVERY,
    /**
     * 待收货
     */
    WAITING_RECEIVE,
    /**
     * 已完成
     */
    FINISHED,
    /**
     * 已关闭
     */
    CLOSED,

    /**
     * 分支条件
     */

    /**
     * 检查是否为公益
     */
    CHECK_LOVE,
    /**
     * 检查是否为自提
     */
    CHECK_DELIVERY,

}
