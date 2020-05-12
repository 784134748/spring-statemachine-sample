package org.apframework.statemachine.core.action.biz;

import lombok.extern.slf4j.Slf4j;
import org.apframework.statemachine.core.enums.TradeEvents;
import org.apframework.statemachine.core.enums.TradeStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

/**
 * @author yalonglee
 */
@Slf4j
@Service
public class ConfirmAction implements Action<TradeStates, TradeEvents> {

    @Override
    public void execute(StateContext stateContext) {
        log.info("确认接单");
    }

}
