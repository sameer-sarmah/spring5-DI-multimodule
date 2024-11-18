package category.kafka.pub;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import model.Greeting;

@Component("kafkaPublisher")
public class KafkaPublisher {

	private KafkaTemplate<Long, Object> kafkaTemplate;

	public KafkaPublisher(@Qualifier("kafkaCustomTemplate") KafkaTemplate<Long, Object> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publish(String topic,Greeting greeting) {


		CompletableFuture<SendResult<Long, Object>> future =  kafkaTemplate.<Long,Object>send(topic, greeting.getId(), greeting);
		kafkaTemplate.flush();
		future.thenAccept(result -> {
			 System.out.println("Sent message=[" + result.getProducerRecord().value() +
		              "] with offset=[" + result.getRecordMetadata().offset() + "]");
		});

	}
}
