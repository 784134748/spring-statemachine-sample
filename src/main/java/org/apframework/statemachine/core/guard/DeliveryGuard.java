package org.apframework.statemachine.core.guard;

import lombok.extern.slf4j.Slf4j;
import org.apframework.statemachine.core.enums.Header;
import org.apframework.statemachine.core.enums.TradeEvents;
import org.apframework.statemachine.core.enums.TradeStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

/**
 * @author yalonglee
 */
@Slf4j
@Component
public class DeliveryGuard implements Guard<TradeStates, TradeEvents> {

    @Override
    public boolean evaluate(StateContext<TradeStates, TradeEvents> stateContext) {
        String tradeNo = (String) stateContext.getMessage().getHeaders().get(Header.TRADE_NO);
        log.info("交易号：{}，是否发货", tradeNo);
        return false;
    }

}
