package protocole.data.connection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;


public class LoginConfirmation implements IData {
    private boolean isConnected;

    @JsonCreator
    public LoginConfirmation(@JsonProperty("isConnected") boolean isConnected){
        this.isConnected = isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean getConnected(){
        return isConnected;
    }
}
