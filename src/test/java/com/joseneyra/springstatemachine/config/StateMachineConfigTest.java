package com.joseneyra.springstatemachine.config;

import com.joseneyra.springstatemachine.domain.PaymentEvent;
import com.joseneyra.springstatemachine.domain.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;


@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<PaymentState, PaymentEvent> factory;

    @Test
    void testNewStateMachine() {
        StateMachine<PaymentState, PaymentEvent> fsm = factory.getStateMachine(UUID.randomUUID());

        fsm.start();
        System.out.println(fsm.getState().toString());

        fsm.sendEvent(PaymentEvent.PRE_AUTHORIZE);
        System.out.println(fsm.getState().toString());

        fsm.sendEvent(PaymentEvent.AUTH_APPROVED);
        System.out.println(fsm.getState().toString());
    }
}