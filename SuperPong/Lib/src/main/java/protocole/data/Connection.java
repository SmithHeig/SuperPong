package protocole.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


public class Connection implements IData{
    private boolean isConnected;

    @JsonCreator
    public Connection(@JsonProperty("isConnected") boolean isConnected){
        this.isConnected = isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean getConnected(){
        return isConnected;
    }
}
