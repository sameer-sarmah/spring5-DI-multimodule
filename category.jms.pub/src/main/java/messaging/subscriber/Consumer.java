package messaging.subscriber;


import messaging.model.Greeting;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination = "standalone.queue" ,containerFactory = "jmsListenerContainerFactory")
    public void consume(Greeting greeting) {
        System.out.println("Received Message: " + greeting.getMessage());
    }
}
