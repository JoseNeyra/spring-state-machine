package com.joseneyra.springstatemachine.config;

import com.joseneyra.springstatemachine.domain.PaymentEvent;
import com.joseneyra.springstatemachine.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@EnableStateMachineFactory          // Tells Spring to generate a state machine component
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent> {

    // Defines the states
    @Override
    public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
        states.withStates()
                .initial(PaymentState.NEW)                      // Sets initial state
                .states(EnumSet.allOf(PaymentState.class))      // Loads all states
                .end(PaymentState.AUTH)                         // Defines happy path end state
                .end(PaymentState.PRE_AUTH_ERROR)               // Defines first error end state
                .end(PaymentState.AUTH_ERROR);                  // Defines second error end state
    }

    // Defines the transitions
    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {
        transitions
                .withExternal().source(PaymentState.NEW).target(PaymentState.NEW).event(PaymentEvent.PRE_AUTHORIZE)
                .and()
                .withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH).event(PaymentEvent.PRE_AUTH_APPROVED)
                .and()
                .withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH_ERROR).event(PaymentEvent.PRE_AUTH_DECLINED);
    }

    // Sets up a listener, at this time we're just logging state changes
    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentState, PaymentEvent> config) throws Exception {
        StateMachineListenerAdapter<PaymentState, PaymentEvent> adapter = new StateMachineListenerAdapter<>(){

            public void stateChanged(State from, State to) {
                log.info(String.format("stateChanged(from: %s, to: %s)", from, to));
            }
        };
        config.withConfiguration().listener(adapter);
    }
}
