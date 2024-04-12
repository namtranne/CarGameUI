package socket.object;

import java.io.Serializable;
import java.util.List;

public class Game  implements Serializable {

    private int GOAL_SCORE;

    public Game(int GOAL_SCORE, List<Player> playerLIst) {
        this.GOAL_SCORE = GOAL_SCORE;
        this.playerLIst = playerLIst;
    }

    public Player getPlayer(String playerName) {
        for(Player player : playerLIst) {
            if(player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public int getGOAL_SCORE() {
        return GOAL_SCORE;
    }

    public void setGOAL_SCORE(int GOAL_SCORE) {
        this.GOAL_SCORE = GOAL_SCORE;
    }

    public Player getVictoryPlayer() {
        return victoryPlayer;
    }

    public void setVictoryPlayer(Player victoryPlayer) {
        this.victoryPlayer = victoryPlayer;
    }

    private Player victoryPlayer;

    private static final long serialVersionUID = 1L;

    public List<Player> getPlayerLIst() {
        return playerLIst;
    }

    public void setPlayerLIst(List<Player> playerLIst) {
        this.playerLIst = playerLIst;
    }

    private List<Player> playerLIst;

    public void updateVictoryPlayer() {
        int point = GOAL_SCORE;
        for(Player player : playerLIst) {
            if(player.getPoint() >= point) {
                point = player.getPoint();
                victoryPlayer = player;
            }
        }
    }
}
