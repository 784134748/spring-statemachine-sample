package org.apframework.statemachine.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;


/**
 * @author yalonglee
 */
@Slf4j
@WithStateMachine(id = "trade_state_machine")
public class StatemachineMonitor {

    @OnStateChanged(source = "WAITING_PAY")
    public void sourceWaitingPay() {
        log.info("当前值：待支付。");
    }

    @OnTransition(target = "WAITING_CONFIRM")
    public void targetWaitingConfirm() {
        log.info("期望值：待确认，");
    }

}
