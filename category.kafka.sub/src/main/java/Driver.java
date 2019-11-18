import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import category.kafka.sub.KafkaSubscriber;
import category.kafka.sub.config.KafkaSubscriberConfig;

 
public class Driver {
 
    public static void main(String[] args) throws InterruptedException {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(KafkaSubscriberConfig.class);
    	KafkaSubscriber kafkaSubscriber = (KafkaSubscriber)ctx.getBean("kafkaSubscriber", KafkaSubscriber.class);
    	Thread.sleep(100000);
    }
}