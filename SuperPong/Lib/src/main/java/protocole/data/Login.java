package protocole.data;

public class Login implements IData{
    private String username;
    private String password;

    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
