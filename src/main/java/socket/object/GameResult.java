package socket.object;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GameResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Player> playerRanking = new LinkedList<>();

    public GameResult(List<Player> playerList) {
        List<Player> winPlayers = playerList.stream().filter(player -> !player.isLosesTurn()).collect(Collectors.toList());
        List<Player> losePlayers = playerList.stream().filter(Player::isLosesTurn).collect(Collectors.toList());
        winPlayers.sort((a,b) -> a.getPoint() - b.getPoint());
        losePlayers.sort((a,b)->a.getPoint() - b.getPoint());
        playerRanking.addAll(winPlayers);
        playerRanking.addAll(losePlayers);
    }

    public List<Player> getPlayerRanking() {
        return playerRanking;
    }
}
