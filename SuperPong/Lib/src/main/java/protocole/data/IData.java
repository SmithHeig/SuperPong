package protocole.data;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import protocole.data.connection.LoginConfirmation;
import protocole.data.connection.Login;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value=Login.class, name = "Login"),
        @JsonSubTypes.Type(value=LoginConfirmation.class, name = "LoginConfirmation")
})
public interface IData {
    String toString();
}
