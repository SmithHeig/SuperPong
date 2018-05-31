package protocole.data.connection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;


public class LoginConfirmation implements IData {
    private boolean connected;

    @JsonCreator
    public LoginConfirmation(@JsonProperty(value="isConnected") boolean connected){
        this.connected = connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @JsonProperty(value="isConnected")
    public boolean isConnected() {
        return connected;
    }

    public String toString(){
        return connected ? "true": "false";
    }
}
