package game;

public class PlayerInfo {
    public String username;
    public boolean ready = false;

    public PlayerInfo(String username) {
        this.username = username;
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
