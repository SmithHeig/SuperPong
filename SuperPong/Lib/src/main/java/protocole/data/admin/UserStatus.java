package protocole.data.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class UserStatus implements IData {
    private Boolean isAdmin;

    @JsonCreator
    public UserStatus(@JsonProperty("isAdmin") boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    @JsonProperty("isAdmin")
    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
