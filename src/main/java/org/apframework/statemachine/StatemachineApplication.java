package org.apframework.statemachine;

import org.apframework.statemachine.core.Statemachine;
import org.apframework.statemachine.core.enums.TradeEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yalonglee
 */
@SpringBootApplication
public class StatemachineApplication implements CommandLineRunner {

    @Autowired
    private Statemachine statemachineService;

    public static void main(String[] args) {
        SpringApplication.run(StatemachineApplication.class, args);
    }

    @Override
    public void run(String... strings) {

        Map<String, Object> context = new HashMap<>(16);
        statemachineService.execute("1314520", TradeEvents.PAY_SUCCEED, context);
//        statemachineService.execute("1314520", TradeEvents.PAY_TIMEOUT, context);
        statemachineService.execute("1314520", TradeEvents.CONFIRM, context);
//        statemachineService.execute("1314520", TradeEvents.REJECT, context);

    }

}
