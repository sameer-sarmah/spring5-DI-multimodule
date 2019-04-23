package category.jms.sub;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component("jmsSubscriber")
public class ActiveMQSub<T> {
	@JmsListener(destination = "northwind")
	public void onMessage(T message) {
		System.out.println(message);
	}
}
