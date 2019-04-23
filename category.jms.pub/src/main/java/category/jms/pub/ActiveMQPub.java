package category.jms.pub;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import api.JMSPublisher;

@Component("jmsPublisher")
public class ActiveMQPub<T> implements JMSPublisher<T> {

	private JmsTemplate jms;

	public ActiveMQPub(JmsTemplate jms) {
		super();
		this.jms = jms;
	}

	public void publish(T subject,String topic) {
		jms.convertAndSend(topic, subject);
	}
	
	
	
}
