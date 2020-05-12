package org.apframework.statemachine.core;

import lombok.extern.slf4j.Slf4j;
import org.apframework.statemachine.core.enums.Header;
import org.apframework.statemachine.core.enums.TradeEvents;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;


/**
 * @author yalonglee
 */
@Slf4j
@WithStateMachine(id = "trade_state_machine")
public class StatemachineMonitor {

    @OnStateChanged(source = "WAITING_PAY", target = "WAITING_CONFIRM")
    public boolean sourceWaitingPay(Message<TradeEvents> message) {
        String tradeNo = (String) message.getHeaders().get(Header.TRADE_NO);
        log.info("交易号：{}，变更值：待确认，当前值：待支付。", tradeNo);
        return true;
    }

}
