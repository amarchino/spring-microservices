package guru.springframework.msscssm.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;

@SpringBootTest
class PaymentServiceImplTest {
	
	@Autowired private PaymentService paymentService;
	@Autowired private PaymentRepository paymentRepository;
	private Payment payment;

	@BeforeEach
	void setUp() throws Exception {
		payment = Payment.builder().amount(new BigDecimal("12.99")).build();
	}

	@Test
	@Transactional
	void preAuth() {
		Payment savedPayment = paymentService.newPayment(payment);
		System.out.println("Should be NEW: " + savedPayment.getState());
		assertThat(savedPayment.getState()).isEqualTo(PaymentState.NEW);
		StateMachine<PaymentState, PaymentEvent> stateMachine = paymentService.preAuth(savedPayment.getId());
		Payment preAuthedPayment = paymentRepository.getById(savedPayment.getId());
		System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR: " + stateMachine.getState().getId());
		System.out.println(preAuthedPayment);
	}
	
	@Transactional
	@RepeatedTest(10)
	void auth(RepetitionInfo repetitionInfo) {
		Payment savedPayment = paymentService.newPayment(payment);
		StateMachine<PaymentState, PaymentEvent> preAuthStateMachine = paymentService.preAuth(savedPayment.getId());
		if(preAuthStateMachine.getState().getId() != PaymentState.PRE_AUTH) {
			System.out.println(repetitionInfo.getCurrentRepetition()  + " - Payment failed pre-auth...");
			return;
		}
		System.out.println(repetitionInfo.getCurrentRepetition()  + " - Payment is pre-authorized");
		StateMachine<PaymentState, PaymentEvent> authStateMachine = paymentService.authorizePayment(savedPayment.getId());
		Payment authedPayment = paymentRepository.getById(savedPayment.getId());
		System.out.println(repetitionInfo.getCurrentRepetition()  + " - Should be AUTH or AUTH_ERROR: " + authStateMachine.getState().getId());
		System.out.println(authedPayment);
	}

}






