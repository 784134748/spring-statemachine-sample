package org.apframework.statemachine.core;

import lombok.extern.slf4j.Slf4j;
import org.apframework.statemachine.enums.TradeEvents;
import org.apframework.statemachine.enums.TradeStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author yalonglee
 */
@Service
@Slf4j
public class Statemachine {

    @Autowired
    private StateMachinePersister<TradeStates, TradeEvents, Long> stateMachinePersist;

    @Autowired(required = false)
    private StateMachineFactory<TradeStates, TradeEvents> stateMachineFactory;

    public void execute(Long bizId, TradeEvents event, Map<String, Object> context) {
        // uuid
        final UUID uuid = UUID.randomUUID();
        // 利用随机ID创建状态机，创建时没有与具体定义状态机绑定
        StateMachine<TradeStates, TradeEvents> stateMachine = stateMachineFactory.getStateMachine(uuid);
        stateMachine.start();

        try {
            // 在BizStateMachinePersist的restore过程中，绑定turnstileStateMachine状态机相关事件监听
            stateMachinePersist.restore(stateMachine, bizId);
            // 注入Map<String, Object> context内容到message中
            MessageBuilder<TradeEvents> messageBuilder = MessageBuilder
                    .withPayload(event)
                    .setHeader("bizId", bizId);
            if (null != context) {
                context.entrySet().forEach(p -> messageBuilder.setHeader(p.getKey(), p.getValue()));
            }
            Message<TradeEvents> message = messageBuilder.build();

            // 发送事件，返回是否执行成功
            boolean success = stateMachine.sendEvent(message);
            if (success) {
                stateMachinePersist.persist(stateMachine, bizId);
            } else {
                log.error("bizId：{}，生成故障单：{}", bizId, context);
            }
        } catch (Exception ex) {
            log.error("bizId：{}，当前context：{}，捕获异常堆栈信息：{}", bizId, context, ex);
        } finally {
            stateMachine.stop();
        }

    }

}
