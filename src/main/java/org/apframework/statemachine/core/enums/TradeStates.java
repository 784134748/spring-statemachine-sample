package org.apframework.statemachine.core.enums;


/**
 * @author yalonglee
 */
public enum TradeStates {

    /**
     * 待支付
     */
    WAITING_PAY,
    /**
     * 待确认
     */
    WAITING_CONFIRM,
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
    CLOSED

}
