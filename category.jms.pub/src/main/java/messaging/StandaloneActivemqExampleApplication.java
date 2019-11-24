package messaging;

import messaging.model.Greeting;
import messaging.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;


public class StandaloneActivemqExampleApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
		Greeting greeting = new Greeting();
		greeting.setMessage("hello");
		JmsTemplate jmsTemplate = (JmsTemplate)ctx.getBean(JmsTemplate.class);
		jmsTemplate.convertAndSend("standalone.queue", greeting);
	}
}
