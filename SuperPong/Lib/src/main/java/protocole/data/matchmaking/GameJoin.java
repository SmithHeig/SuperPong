package protocole.data.matchmaking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class GameJoin implements IData {
    private boolean haveJoin;

    @JsonCreator
    public GameJoin(@JsonProperty("haveJoin") boolean haveJoin){
        this.haveJoin = haveJoin;
    }

    @JsonProperty(value="haveJoin")
    public void setHaveJoin(boolean haveJoin) {
        this.haveJoin = haveJoin;
    }

    public boolean isHaveJoin() {
        return haveJoin;
    }
}
