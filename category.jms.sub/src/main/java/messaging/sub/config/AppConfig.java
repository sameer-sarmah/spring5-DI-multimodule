package messaging.sub.config;

import messaging.converter.CustomMessageConverter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@EnableJms
@Configuration
@ComponentScan("category.jms")
@PropertySource({"activemq.properties","application.properties"})
public class AppConfig {
    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Value("${activemq.username}")
    private String username;

    @Value("${activemq.password}")
    private String password;

    @Value("${activemq.destination}")
    private String destination;

    @Autowired
    private CustomMessageConverter messageConverter;

    @Bean
    @DependsOn("activeMQConnectionFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
           @Qualifier("activeMQConnectionFactory") ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAutoStartup(true);
        factory.setConcurrency("1-5");
        return factory;
    }


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
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory());
        jmsTemplate.setDefaultDestination(activeMQQueue());
        return jmsTemplate;
    }
}
