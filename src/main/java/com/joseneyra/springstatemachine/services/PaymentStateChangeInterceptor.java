package com.joseneyra.springstatemachine.services;

import com.joseneyra.springstatemachine.domain.Payment;
import com.joseneyra.springstatemachine.domain.PaymentEvent;
import com.joseneyra.springstatemachine.domain.PaymentState;
import com.joseneyra.springstatemachine.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository paymentRepository;

    // Intercepts a state change, and if a message is present,
    // gets the headers from the message, then gets the paymentId, and stores the payment with its state
    // in the db
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message,
                               Transition<PaymentState, PaymentEvent> transition,
                               StateMachine<PaymentState, PaymentEvent> stateMachine) {
        Optional.ofNullable(message).flatMap(msg ->
                Optional.ofNullable((Long) msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                .ifPresent(paymentId -> {
            Payment payment = paymentRepository.getReferenceById(paymentId);
            payment.setState(state.getId());
            paymentRepository.save(payment);
        });
    }
}
