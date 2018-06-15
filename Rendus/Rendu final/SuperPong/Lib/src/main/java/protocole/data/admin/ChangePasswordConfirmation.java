package protocole.data.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class ChangePasswordConfirmation implements IData {
    private boolean passwordHavedChanged;

    @JsonCreator
    public ChangePasswordConfirmation(@JsonProperty("passwordHavedChanged") boolean passwordHavedChanged){
        this.passwordHavedChanged = passwordHavedChanged;
    }

    @JsonProperty("passwordHavedChanged")
    public boolean isPasswordHavedChanged(){
        return passwordHavedChanged;
    }

    public void setPasswordHavedChanged(boolean passwordHavedChanged){
        this.passwordHavedChanged = passwordHavedChanged;
    }
}
