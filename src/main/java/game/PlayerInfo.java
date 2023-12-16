package game;

public class PlayerInfo {
    public String username;
    public boolean ready;

    public PlayerInfo(String username) {
        this.username = username;
        this.ready=false;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
