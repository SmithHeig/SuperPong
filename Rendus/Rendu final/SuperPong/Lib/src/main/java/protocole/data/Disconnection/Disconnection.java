package protocole.data.Disconnection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class Disconnection implements IData{
    String username;

    @JsonCreator
    public Disconnection(@JsonProperty(value = "username") String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString(){
        return username + " try to disconnect";
    }
}
