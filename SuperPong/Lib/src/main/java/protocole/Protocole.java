package protocole;

import com.fasterxml.jackson.annotation.JsonInclude;
import protocole.data.IData;

/**
 * Capsule envoyé en travers le réseau pour communiqué entre le client et le serveur
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Protocole {
    private String  name; // nom de la communication
    private IData data; // contenu du protocole

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
}
