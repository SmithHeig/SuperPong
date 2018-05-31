package protocole.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.LoginConfirmation;
import protocole.data.connection.Login;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value=LoginConfirmation.class, name = "LoginConfirmation"),
        @JsonSubTypes.Type(value=Login.class, name = "Login"),
        @JsonSubTypes.Type(value=DisconnectionConfirmation.class, name="DisconnectionConfirmation"),
        @JsonSubTypes.Type(value=Disconnection.class, name="Disconnection")
})
public interface IData {
    String toString(); // A voir si le abstract est usless
}
