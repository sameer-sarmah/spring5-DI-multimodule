package category.kafka.pub;

import java.util.Date;
import java.util.Random;

import model.Greeting;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component("kafkaPublisher")
public class KafkaPublisher {

	private KafkaTemplate<Long, Object> kafkaTemplate;

	public KafkaPublisher(@Qualifier("kafkaCustomTemplate") KafkaTemplate<Long, Object> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publish(String topic,Greeting greeting) {


		ListenableFuture<SendResult<Long, Object>> future =  kafkaTemplate.<Long,Object>send(topic, greeting.getId(), greeting);
		kafkaTemplate.flush();
	    future.addCallback(new ListenableFutureCallback<SendResult<Long, Object>>() {
	    	 
	        @Override
	        public void onSuccess(SendResult<Long, Object> result) {
	            System.err.println("Sent message=[" + result.getProducerRecord().value() +
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	            System.err.println("Unable to send message due to : " + ex.getMessage());
	        }
	    });
	}
}
