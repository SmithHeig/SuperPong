package protocole.data.Disconnection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class DisconnectionConfirmation implements IData{
    boolean haveBeenDisconnected;

    @JsonCreator
    public DisconnectionConfirmation(@JsonProperty boolean haveBeenDisconnected){
        this.haveBeenDisconnected = haveBeenDisconnected;
    }

    public void setHaveBeenDisconnected(boolean haveBeenDisconnected){
        this.haveBeenDisconnected = haveBeenDisconnected;
    }

    public boolean getHaveBeenDisconnected(){
        return haveBeenDisconnected;
    }

    public String toString(){
        return haveBeenDisconnected ? "true" : "false";
    }
}
