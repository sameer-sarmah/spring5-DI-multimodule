package category.jms.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;

@ComponentScan("category")
@PropertySource("activemq.properties")
@Configuration
public class ActiveMQConfig {

	@Value("${activemq.broker-url}")
	private String brokerUrl;
	
	@Value("${activemq.username}")
	private String username;
	
	@Value("${activemq.password}")
	private String password;

	@Value("${activemq.destination}")
	private String destination;
	
	@Bean
	public ConnectionFactory activeMQConnectionFactory(){
		ActiveMQConnectionFactory connectionfactory =new ActiveMQConnectionFactory();
		connectionfactory.setPassword(password);
		connectionfactory.setBrokerURL(brokerUrl);
		connectionfactory.setUserName(username);
		return connectionfactory;
	}
	
	@Bean
	public Queue activeMQQueue() {
		ActiveMQQueue queue=new ActiveMQQueue(destination);
		return queue;
	}
	
	@Bean
    @DependsOn({"activeMQConnectionFactory","activeMQQueue"})
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate=new JmsTemplate();
		jmsTemplate.setConnectionFactory(activeMQConnectionFactory());
		jmsTemplate.setDefaultDestination(activeMQQueue());
		return jmsTemplate;
	}
}
