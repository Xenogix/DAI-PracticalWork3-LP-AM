package ch.heigvd.shared.converter;

import ch.heigvd.shared.abstractions.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CommandConverter {

    public static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static byte[] serialize(Command command) throws RuntimeException {

        try {
            String json = mapper.writeValueAsString(command);
            return json.getBytes(CHARSET);
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T extends Command> T deserialize(byte[] data, Class<T> outClass) throws RuntimeException  {
        try {
            String json = new String(data, CHARSET);
            return mapper.readValue(json, outClass);
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
