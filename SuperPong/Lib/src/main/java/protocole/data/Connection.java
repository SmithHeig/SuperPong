package protocole.data;

public class Connection implements IData{
    private boolean isConnected;

    public Connection(boolean isConnected){
        this.isConnected = isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean getConnected(){
        return isConnected;
    }
}
