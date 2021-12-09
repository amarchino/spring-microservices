package guru.springframework.msscssm.services;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

	@Override
	public Payment newPayment(Payment payment) {
		payment.setState(PaymentState.NEW);
		return paymentRepository.save(payment);
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		// TODO Auto-generated method stub
		return sm;
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		// TODO Auto-generated method stub
		return sm;
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		// TODO Auto-generated method stub
		return sm;
	}

	private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
		Payment payment = paymentRepository.getById(paymentId);
		StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory.getStateMachine(payment.getId().toString());
		stateMachine.stop();
		stateMachine.getStateMachineAccessor()
			.doWithAllRegions(sma -> sma.resetStateMachine(new DefaultStateMachineContext<PaymentState, PaymentEvent>(payment.getState(), null, null, null)));
		stateMachine.start();
		return stateMachine;
	}
}
