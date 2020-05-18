package org.apframework.statemachine.core;

import org.apframework.statemachine.core.enums.TradeEvents;
import org.apframework.statemachine.core.enums.TradeStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author yalonglee
 */
@Configuration
@EnableStateMachineFactory
public class StatemachineConfig extends EnumStateMachineConfigurerAdapter<TradeStates, TradeEvents> {

    @Autowired
    private BizStateMachinePersist bizStateMachinePersist;

    @Autowired
    private Map<String, Action<TradeStates, TradeEvents>> actions;

    @Autowired
    private Map<String, Guard<TradeStates, TradeEvents>> guards;

    @Bean
    public StateMachinePersister<TradeStates, TradeEvents, String> stateMachinePersist() {
        return new DefaultStateMachinePersister(bizStateMachinePersist);
    }

    @Override
    public void configure(StateMachineStateConfigurer<TradeStates, TradeEvents> states)
            throws Exception {
        states
                .withStates()
                //初识状态：WAITING_PAY
                .initial(TradeStates.WAITING_PAY)
                //分支选择
                .choice(TradeStates.CHECK_LOVE)
                .choice(TradeStates.CHECK_DELIVERY)
                //所有状态集合
                .states(EnumSet.allOf(TradeStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TradeStates, TradeEvents> transitions)
            throws Exception {
        transitions
                //超时未支付
                .withExternal()
                .source(TradeStates.WAITING_PAY).target(TradeStates.CLOSED)
                .event(TradeEvents.PAY_TIMEOUT)
                .action(actions.get("closeAction"))

                //支付成功
                .and()
                .withExternal()
                .source(TradeStates.WAITING_PAY).target(TradeStates.WAITING_CONFIRM)
                .event(TradeEvents.PAY_SUCCEED)
                .action(actions.get("paymentAction"))

                //确认接单
                .and()
                .withExternal()
                .source(TradeStates.WAITING_CONFIRM).target(TradeStates.CHECK_LOVE)
                .event(TradeEvents.CONFIRM)
                .and()
                .withChoice()
                .source(TradeStates.CHECK_LOVE)
                .first(TradeStates.FINISHED, guards.get("loveGuard"), actions.get("completeAction"))
                .last(TradeStates.CHECK_DELIVERY)
                .and()
                .withChoice()
                .source(TradeStates.CHECK_DELIVERY)
                .first(TradeStates.WAITING_RECEIVE, guards.get("deliveryGuard"), actions.get("completeAction"))
                .last(TradeStates.WAITING_DELIVERY, actions.get("completeAction"))

                //拒绝接单
                .and()
                .withExternal()
                .source(TradeStates.WAITING_CONFIRM).target(TradeStates.CLOSED)
                .event(TradeEvents.REJECT)
                .action(actions.get("rejectAction"))

                //买家提货
                .and()
                .withExternal()
                .source(TradeStates.WAITING_RECEIVE).target(TradeStates.FINISHED)
                .event(TradeEvents.DELIVERY)
                .action(actions.get("deliveryAction"))

                //确认收货
                .and()
                .withExternal()
                .source(TradeStates.WAITING_RECEIVE).target(TradeStates.FINISHED)
                .event(TradeEvents.RECEIVE)
                .action(actions.get("receiveAction"))

                //同意退款
                .and()
                .withExternal()
                .source(TradeStates.WAITING_RECEIVE).target(TradeStates.CLOSED)
                .event(TradeEvents.REFUND)
                .action(actions.get("refundAction"))

                //取消订单
                .and()
                .withExternal()
                .source(TradeStates.WAITING_PAY).target(TradeStates.CLOSED)
                .event(TradeEvents.CLOSE)
                .action(actions.get("closeAction"))

                //超时处理
                .and()
                .withExternal()
                .source(TradeStates.WAITING_RECEIVE).target(TradeStates.FINISHED)
                .event(TradeEvents.REFUND)
                .action(actions.get("completeAction"))
        ;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<TradeStates, TradeEvents> config)
            throws Exception {
        config.withConfiguration()
                .machineId("trade_state_machine")
        ;
    }

}
