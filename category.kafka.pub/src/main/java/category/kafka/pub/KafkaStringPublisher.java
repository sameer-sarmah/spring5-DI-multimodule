package category.kafka.pub;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component("kafkaStringPublisher")
public class KafkaStringPublisher {

	private KafkaTemplate<String, String> kafkaTemplate;

	public KafkaStringPublisher(@Qualifier("kafkaStringTemplate") KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publish(String topic,String message) {
		String key = new Date().toString();
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send(topic, 0, key, message);
		kafkaTemplate.flush();
	    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
	    	 
	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	            System.err.println("Sent message=[" + message + 
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	            System.err.println("Unable to send message=["
	              + message + "] due to : " + ex.getMessage());
	        }
	    });
	}
}
