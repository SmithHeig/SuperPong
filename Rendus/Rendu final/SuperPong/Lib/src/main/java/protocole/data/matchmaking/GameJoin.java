package protocole.data.matchmaking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class GameJoin implements IData {
    private boolean haveJoin;
    private int id;

    @JsonCreator
    public GameJoin(@JsonProperty("haveJoin") boolean haveJoin, @JsonProperty("id") int id){
        this.haveJoin = haveJoin;
        this.id = id;
    }

    @JsonProperty(value="haveJoin")
    public void setHaveJoin(boolean haveJoin) {
        this.haveJoin = haveJoin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isHaveJoin() {
        return haveJoin;
    }
}
