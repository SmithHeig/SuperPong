package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import protocole.Protocole;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.Login;
import protocole.data.connection.LoginConfirmation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonMapper {
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(JsonMapper.class.getName());

    /* INSTANCE */
    private final static JsonMapper instance = new JsonMapper();

    private JsonMapper(){}

    public static JsonMapper getInstance() {
        return instance;
    }

    /* OBJECT MAPPER */
    private static final ObjectMapper JSONobjectMapper = new ObjectMapper();
    static {
        JSONobjectMapper.registerSubtypes(new NamedType(LoginConfirmation.class, "LoginConfirmation"));
        JSONobjectMapper.registerSubtypes(new NamedType(Login.class, "Login"));
        JSONobjectMapper.registerSubtypes(new NamedType(Disconnection.class, "Disconnection"));
        JSONobjectMapper.registerSubtypes(new NamedType(DisconnectionConfirmation.class, "DisconnectionConfirmation"));
    }

    public static String convertToString(Protocole protocole){
        try {
            return JSONobjectMapper.writeValueAsString(protocole);
        } catch(JsonProcessingException e){
            LOG.log(Level.SEVERE, "Can not serialize with exception: " + e.getMessage());
        }
        return null;
    }

    public static Protocole convertToProtocole(String msgJson){
        try {
            return JSONobjectMapper.readValue(msgJson, Protocole.class);
        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not serialize with exception: " + e.getMessage());
        }
        return null;
    }
}
