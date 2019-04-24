import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import category.kafka.config.KafkaPublisherConfig;
import category.kafka.pub.KafkaStringPublisher;
 
public class Driver {
 
    public static void main(String[] args) {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(KafkaPublisherConfig.class);
    	KafkaStringPublisher kafkaPublisher = (KafkaStringPublisher)ctx.getBean("kafkaStringPublisher", KafkaStringPublisher.class);
    	kafkaPublisher.publish("northwind","helloworld");
    }
}