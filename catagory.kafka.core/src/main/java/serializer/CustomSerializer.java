package serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CustomSerializer implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Object data) {
        byte[] serializedBytes = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(data);
            serializedBytes = jsonString.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializedBytes;
    }

    @Override
    public void close() {

    }
}