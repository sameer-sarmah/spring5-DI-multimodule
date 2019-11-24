package messaging.converter;

import messaging.model.Greeting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;


@Component
public class CustomMessageConverter implements MessageConverter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomMessageConverter.class);

    ObjectMapper mapper;

    public CustomMessageConverter() {
        mapper = new ObjectMapper();
    }

    @Override
    public Message toMessage(Object object, Session session)
            throws JMSException {
        Greeting greeting = (Greeting) object;
        String payload = null;
        try {
            payload = mapper.writeValueAsString(greeting);
            LOGGER.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            LOGGER.error("error converting form person", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        LOGGER.info("inbound json='{}'", payload);

        Greeting greeting = null;
        try {
            greeting = mapper.readValue(payload, Greeting.class);
        } catch (Exception e) {
            LOGGER.error("error converting to person", e);
        }

        return greeting;
    }
}