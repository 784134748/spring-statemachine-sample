package org.apframework.statemachine.core;

import lombok.extern.slf4j.Slf4j;
import org.apframework.statemachine.enums.TradeEvents;
import org.apframework.statemachine.enums.TradeStates;
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
public class BizStateMachinePersist implements StateMachinePersist<TradeStates, TradeEvents, Long> {

    static Map<Long, TradeStates> cache = new HashMap<>(16);

    @Override
    public void write(StateMachineContext<TradeStates, TradeEvents> stateMachineContext, Long bizId) {
        cache.put(bizId, stateMachineContext.getState());
        log.info("bizId：{}，持久化状态，state：{}", bizId, stateMachineContext.getState());
    }

    @Override
    public StateMachineContext<TradeStates, TradeEvents> read(Long bizId) {
        log.info("bizId：{}，当前状态，state：{}", bizId, cache.get(bizId));
        // TODO 获取分布式锁
        log.info("bizId：{}，成功获取分布式锁", bizId, cache.get(bizId));
        return cache.containsKey(bizId) ?
                new DefaultStateMachineContext<>(cache.get(bizId), null, null, null, null, "trade_state_machine") :
                new DefaultStateMachineContext<>(TradeStates.WAITING_PAY, null, null, null, null, "trade_state_machine");
    }

}
