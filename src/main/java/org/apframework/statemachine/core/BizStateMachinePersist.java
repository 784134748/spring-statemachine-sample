package org.apframework.statemachine.core;

import lombok.extern.slf4j.Slf4j;
import org.apframework.statemachine.core.enums.TradeEvents;
import org.apframework.statemachine.core.enums.TradeStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yalonglee
 */
@Slf4j
@Component
public class BizStateMachinePersist implements StateMachinePersist<TradeStates, TradeEvents, String> {

    static Map<String, TradeStates> cache = new HashMap<>(16);

    @Override
    public void write(StateMachineContext<TradeStates, TradeEvents> stateMachineContext, String tradeNo) {
        cache.put(tradeNo, stateMachineContext.getState());
        log.info("交易号：{}，变更状态写入：{}", tradeNo, stateMachineContext.getState());
    }

    @Override
    public StateMachineContext<TradeStates, TradeEvents> read(String tradeNo) {
        // TODO 获取分布式锁
        log.info("交易号：{}，成功获取分布式锁", tradeNo, cache.get(tradeNo));
        log.info("交易号：{}，当前状态读取：{}", tradeNo, cache.get(tradeNo));
        return cache.containsKey(tradeNo) ?
                new DefaultStateMachineContext<>(cache.get(tradeNo), null, null, null, null, "trade_state_machine") :
                new DefaultStateMachineContext<>(TradeStates.WAITING_PAY, null, null, null, null, "trade_state_machine");
    }

}
