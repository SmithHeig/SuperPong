package protocole;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

/**
 * Capsule envoyé en travers le réseau pour communiqué entre le client et le serveur
 */
public class Protocole {
    private String  name; // nom de la communication
    private IData data; // contenu du protocole

    @JsonCreator
    public Protocole(@JsonProperty("name") String name, @JsonProperty("data") IData data){
        this.name = name;
        this.data = data;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setData(IData data){
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public IData getData() {
        return data;
    }

    public String toString(){
        return name + " " + data;
    }
}
