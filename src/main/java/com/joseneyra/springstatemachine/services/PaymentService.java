package com.joseneyra.springstatemachine.services;

import com.joseneyra.springstatemachine.domain.Payment;
import com.joseneyra.springstatemachine.domain.PaymentEvent;
import com.joseneyra.springstatemachine.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);

}
