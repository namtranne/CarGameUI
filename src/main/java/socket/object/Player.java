package socket.object;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    public Player(String name, int point) {
        this.name = name;
        this.point = point;
    }

    private String name;

    private  boolean isLosesTurn;

    public boolean isLosesTurn() {
        return isLosesTurn;
    }

    public Player(Player player) {
        this.name = player.name;
        this.point = player.point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    private int point;
}
