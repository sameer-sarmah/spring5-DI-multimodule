import messaging.sub.ActiveMQSub;
import messaging.sub.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JmsSub {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ActiveMQSub activeMQSub = (ActiveMQSub) ctx.getBean("jmsSubscriber", ActiveMQSub.class);

    }
}