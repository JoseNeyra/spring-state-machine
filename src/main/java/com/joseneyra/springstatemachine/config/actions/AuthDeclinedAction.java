package com.joseneyra.springstatemachine.config.actions;

import com.joseneyra.springstatemachine.domain.PaymentEvent;
import com.joseneyra.springstatemachine.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class AuthDeclinedAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        System.out.println("Sending Notification of Auth Declined");
    }
}
