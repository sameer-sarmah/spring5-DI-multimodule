package deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Greeting;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component
public class CustomDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        String jsonStr = new String(data);
        JsonNode parent;
        try {
            ObjectMapper mapper = new ObjectMapper();
            parent = mapper.readTree(jsonStr);
            if(parent.get("type") != null) {
                String type = parent.get("type").asText();
                Object parsedObject;
                if (type.equals(Greeting.class.getName())) {
                    return mapper.readValue(data, Greeting.class);
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }
}
