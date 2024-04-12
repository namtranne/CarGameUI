package scenes;

import cargame.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socket.object.GameResult;
import socket.object.Player;
import ui.MyButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.LinkedList;

import static cargame.Constants.WINDOW_WIDTH;


@Component
public class GameOver extends GameScene implements SceneMethods {

	private MyButton bQuit;

	@Autowired MyMenu menu;

	String tMessage = "Final standing:";

	List<Player> playerRanking = new LinkedList<>();

	public GameOver() {
		initButtons();
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = WINDOW_WIDTH/2 - w/2;
		int y = 270;
		int yOffset = 70;
		bQuit = new MyButton("Back to menu", x, y + yOffset, w, h, false);
	}

	@Override
	public void render(Graphics g) {
		Font defaultFont  = g.getFont();
		Font newFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);

		drawButtons(g);
		drawErrorText(g, newFont);
		drawPlayerRank(g, newFont);

		g.setFont(defaultFont);
	}

	private void drawPlayerRank(Graphics g, Font font) {
		g.setFont(font);
		int offSet = 50;
		g.setColor(Color.RED);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		for(int i =0; i < playerRanking.size() ;i++) {
			Player player = playerRanking.get(i);
			String displayedString = (i+1) + ". "  + player.getName()+ ": " + player.getPoint() + " pts";
			int stringWidth = fontMetrics.stringWidth(displayedString);
			g.drawString(displayedString, WINDOW_WIDTH/2-stringWidth/2, 80 + offSet*i);
		}

	}

	public void drawErrorText(Graphics g, Font font) {
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		int stringWidth = fontMetrics.stringWidth(tMessage);
		g.drawString(tMessage, WINDOW_WIDTH/2 - stringWidth/2, 40);
	}

	private void drawButtons(Graphics g) {
		bQuit.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		return;
	}

	@Override
	public void mouseMoved(int x, int y) {

		bQuit.setMouseOver(false);
		if (bQuit.getBounds().contains(x, y))
			bQuit.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) {
		if (bQuit.getBounds().contains(x, y)) {
			GameState.SetGameState(GameState.MENU);
			menu.getTextField().setText(GameState.playerName);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bQuit.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}

	public void onGameResultReceived(GameResult result) {
		GameState.SetGameState(GameState.GAME_OVER);
		this.playerRanking = result.getPlayerRanking();
	}
}