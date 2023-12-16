package ch.heigvd.data.converter;

import ch.heigvd.data.commands.*;
import ch.heigvd.data.commands.data.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CommandSerializer {

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

    public static Command deserialize(byte[] data) throws RuntimeException  {
        try {
            String json = new String(data, CHARSET);
            Command command = mapper.readValue(json, Command.class);
            Object value = command.getValue();

            switch (command.getCommandType()) {
                case ACCEPT -> command.setValue(mapper.convertValue(value, new TypeReference<AcceptCommandData>() { }));
                case REFUSE -> command.setValue(mapper.convertValue(value, new TypeReference<RefuseCommandData>() { }));
                case UPDATE -> command.setValue(mapper.convertValue(value, new TypeReference<UpdateCommandData>() { }));
                case JOIN -> command.setValue(mapper.convertValue(value, new TypeReference<JoinCommandData>() { }));
                case INPUT -> command.setValue(mapper.convertValue(value, new TypeReference<InputCommandData>() { }));
            }

            return command;
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
