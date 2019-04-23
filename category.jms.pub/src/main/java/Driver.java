import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import category.jms.config.ActiveMQConfig;
import category.jms.pub.ActiveMQPub;
 
public class Driver {
 
    public static void main(String[] args) {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(ActiveMQConfig.class);
    	ActiveMQPub<String> datasource = (ActiveMQPub<String>)ctx.getBean("jmsPublisher", ActiveMQPub.class);
    	datasource.publish("helloworld", "northwind");
    }
}