package protocole.data.Disconnection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class DisconnectionConfirmation implements IData{
    boolean isDisconnected;

    @JsonCreator
    public DisconnectionConfirmation(@JsonProperty boolean haveBeenDisconnected){
        this.isDisconnected = isDisconnected;
    }

    public void setDisconnected(boolean haveBeenDisconnected){
        this.isDisconnected = isDisconnected;
    }

    public boolean isDisconnected(){
        return isDisconnected;
    }

    public String toString(){
        return isDisconnected ? "true" : "false";
    }
}
