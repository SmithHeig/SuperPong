package protocole.data.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class ChangeStatsConfirmation implements IData{
    private boolean statsHavedChanged;

    @JsonCreator
    public ChangeStatsConfirmation(@JsonProperty("statsHavedChanged") boolean statsHavedChanged){
        this.statsHavedChanged = statsHavedChanged;
    }

    @JsonProperty("statsHavedChanged")
    public boolean isStatsHavedChanged(){
        return statsHavedChanged;
    }

    public void setStatsHavedChanged(boolean statsHavedChanged){
        this.statsHavedChanged = statsHavedChanged;
    }
}
