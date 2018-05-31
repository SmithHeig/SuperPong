package protocole.data.Disconnection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class DisconnectionConfirmation implements IData{
    private boolean isDisconnected;

    @JsonCreator
    public DisconnectionConfirmation(@JsonProperty("isDisconnected") boolean isDisconnected){
        this.isDisconnected = isDisconnected;
    }

    public void setDisconnected(boolean isDisconnected){
        this.isDisconnected = isDisconnected;
    }

    @JsonProperty(value="isDisconnected")
    public boolean isDisconnected(){
        return isDisconnected;
    }

    public String toString(){
        return isDisconnected ? "true" : "false";
    }
}
