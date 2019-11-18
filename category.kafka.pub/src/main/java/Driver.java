import model.Greeting;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import category.kafka.config.KafkaPublisherConfig;
import category.kafka.pub.KafkaPublisher;

import java.util.Random;

public class Driver {
 
    public static void main(String[] args) {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(KafkaPublisherConfig.class);
    	KafkaPublisher kafkaPublisher = (KafkaPublisher)ctx.getBean("kafkaPublisher", KafkaPublisher.class);
		Random random = new Random();
		Greeting greeting = new Greeting();
		greeting.setId(random.nextLong());
		greeting.setMessage("helloworld");
    	kafkaPublisher.publish("northwind",greeting);
    }
}