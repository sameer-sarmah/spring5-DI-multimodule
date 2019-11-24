package messaging.sub;

import messaging.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component("jmsSubscriber")
public class ActiveMQSub<T> {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(ActiveMQSub.class);

	@JmsListener(destination = "northwind", containerFactory = "jmsListenerContainerFactory")
	public void onMessage(T message) {
		if(message instanceof Greeting)
			LOGGER.info(((Greeting) message).getMessage());
	}
}
