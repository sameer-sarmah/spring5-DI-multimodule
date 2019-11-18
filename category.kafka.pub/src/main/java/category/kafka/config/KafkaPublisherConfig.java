package category.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import serializer.CustomSerializer;

@PropertySource("classpath:kafka.properties")
@Configuration
@ComponentScan("category")
public class KafkaPublisherConfig {

	@Value(value = "${kafka.bootstrap-server}")
	private String bootstrapAddress;

	@Value(value = "${kafka.topic}")
	private String topic;

//	@Bean
//	public KafkaAdmin kafkaAdmin() {
//		Map<String, Object> configs = new HashMap<>();
//		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//		return new KafkaAdmin(configs);
//	}
//
//	@Bean("northwind-topic")
//	public NewTopic topic() {
//		return new NewTopic(topic, 1, (short) 1);
//	}

	@Bean
	public ProducerFactory<Long, Object> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean("kafkaCustomTemplate")
	public KafkaTemplate<Long, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
