package protocole.data.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class ChangePassword implements IData {
    private String username;
    private String newPassword;

    @JsonCreator
    public ChangePassword(@JsonProperty("username") String username, @JsonProperty("newPassword") String newPassword){
        this.username = username;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getUsername(){return username;}

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setUsername(String username){this.username = username;}
}
