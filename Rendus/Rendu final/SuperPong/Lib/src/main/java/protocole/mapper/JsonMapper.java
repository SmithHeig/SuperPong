package protocole.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import protocole.Protocole;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.Login;
import protocole.data.connection.LoginConfirmation;
import protocole.data.matchmaking.GameJoin;
import protocole.data.matchmaking.InscriptionMatchmaking;
import protocole.data.stats.Stats;
import protocole.game.ClientInfoMove;
import protocole.game.ServerInfo;

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
        JSONobjectMapper.registerSubtypes(new NamedType(GameJoin.class, "GameJoin"));
        JSONobjectMapper.registerSubtypes(new NamedType(InscriptionMatchmaking.class, "InscriptionMatchmaking"));
        JSONobjectMapper.registerSubtypes(new NamedType(ClientInfoMove.class,"ClientInfoMove"));
        JSONobjectMapper.registerSubtypes(new NamedType(ServerInfo.class, "ServerInfo"));
        JSONobjectMapper.registerSubtypes(new NamedType(Stats.class, "Stats"));
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
