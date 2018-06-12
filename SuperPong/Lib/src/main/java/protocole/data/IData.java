package protocole.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.LoginConfirmation;
import protocole.data.connection.Login;
import protocole.data.matchmaking.GameJoin;
import protocole.data.matchmaking.InscriptionMatchmaking;
import protocole.data.stats.Stats;
import protocole.game.ClientInfoMove;
import protocole.game.ServerInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value=LoginConfirmation.class, name = "LoginConfirmation"),
        @JsonSubTypes.Type(value=Login.class, name = "Login"),
        @JsonSubTypes.Type(value=DisconnectionConfirmation.class, name="DisconnectionConfirmation"),
        @JsonSubTypes.Type(value=Disconnection.class, name="Disconnection"),
        @JsonSubTypes.Type(value=InscriptionMatchmaking.class, name="InscriptionMatchmaking"),
        @JsonSubTypes.Type(value= GameJoin.class, name="GameJoin"),
        @JsonSubTypes.Type(value= ClientInfoMove.class, name="ClientInfoMove"),
        @JsonSubTypes.Type(value= ServerInfo.class, name="ServerInfo"),
        @JsonSubTypes.Type(value= Stats.class, name="Stats")
})
public interface IData {
    String toString(); // A voir si le abstract est usless
}
