package category.kafka.sub;

import model.Greeting;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component("kafkaSubscriber")
public class KafkaSubscriber {
	@KafkaListener(
			  topicPartitions = @TopicPartition(topic = "northwind",
			  partitionOffsets = {
			    @PartitionOffset(partition = "0", initialOffset = "0")
			}))
	public void onMessage(@Payload ConsumerRecord message) {

		if(message.value() instanceof Greeting){
			Greeting greeting = (Greeting)message.value();
			System.out.println(greeting.getMessage());
		}
	}
	
}
