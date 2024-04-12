package scenes;


import cargame.GameState;
import cargame.QuestionListener;
import cargame.ServerCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import socket.object.Answer;
import socket.object.Game;
import socket.object.Player;
import ui.MyButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalTime;

import static cargame.Constants.WINDOW_WIDTH;

@Component
public class Playing extends GameScene implements SceneMethods, QuestionListener {


	private int mouseX, mouseY;
	private boolean gamePaused;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	private Game game;

	private int goalPoint = 20, currentPoint = 10;

	public void setQuestion(String question) {
		this.question = question;
	}

	private String question;

	private LocalTime receivedQuestionTime, targetAnswerTime;

	@Autowired
	private JTextField textField;

	private MyButton bSubmit;

	@Autowired
	private Image backgroundImage;

	@Autowired
	private Image carImage;

	public Playing() {
		int w = 150;
		int h = w / 3;
		int x = WINDOW_WIDTH/2 - w/2;
		int y = 270;
		bSubmit = new MyButton("Submit answer", x, y , w, h, true);
	}

	public void update() {
		if (!gamePaused) {
			updateTick();
		}
	}

	@Override
	public void render(Graphics g) {
		Font defaultFont  = g.getFont();
		Font newFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);
		drawImages(g, newFont);
		drawPoints(g, newFont);
		drawTime(g, newFont);
		drawQuestion(g, newFont);
		drawMessage(g, newFont);
		drawButtons(g, defaultFont);
	}

	private void drawMessage(Graphics g, Font font) {
		g.setFont(font);
		g.setColor(Color.RED);
		if(!game.getPlayer(GameState.playerName).isLosesTurn()) return;
		String displayedString = "You've been disqualified from the race!";
		FontMetrics metrics = g.getFontMetrics(font);
		int w = metrics.stringWidth(displayedString);
		int x = WINDOW_WIDTH/2 - w/2;
		int y = 300;
		g.drawString(displayedString, x, y);
	}

	public void drawImages(Graphics g, Font font) {
		g.drawImage(backgroundImage, 0, 0, 840, 150, null);
		int offset = 0;
		if(game == null) {
			return;
		}
		g.setFont(font);
		for(Player player : game.getPlayerLIst()) {
			g.drawImage(carImage, player.getPoint()*60, offset , 60, 30, null);
			offset+=30;
			if(player.getName().equals(GameState.playerName)) {
				g.setColor(Color.RED);

			}
			else {
				g.setColor(Color.WHITE);
				g.drawString(player.getName(), WINDOW_WIDTH/2, offset - 7);
			}
		};
	}

	public void drawPoints(Graphics g, Font font) {
		g.setColor(Color.RED);
		g.setFont(font);
		if(game == null) {
			return;
		}
		g.drawString("Your point: " + game.getPlayerLIst().get(0).getPoint() + "/" + game.getGOAL_SCORE(), WINDOW_WIDTH - 170, 170);
	}

	public void drawTime(Graphics g, Font font) {
		if(targetAnswerTime==null) return;
		long differenceInSeconds = Duration.between(LocalTime.now(), targetAnswerTime).getSeconds();
		g.setColor(Color.RED);
		g.setFont(font);
		if(game == null) {
			return;
		}
		g.drawString("Time: " + differenceInSeconds + " s", 20, 170);
	}

	public void drawQuestion(Graphics g, Font font) {
		if(question != null && !question.isEmpty()) {
			FontMetrics fontMetrics = g.getFontMetrics(font);
			g.setColor(Color.WHITE);
			int stringWidth = fontMetrics.stringWidth("Question: " + question + " =?");
			g.drawString("Question: " + question + " =?", WINDOW_WIDTH/2 - stringWidth/2, 200);
		}
	}

	public void drawButtons(Graphics g, Font font) {
		g.setColor(Color.BLACK);
		g.setFont(font);
		if(!game.getPlayer(GameState.playerName).isLosesTurn()) {
			bSubmit.draw(g);
		}
	}

	private void drawHighlight(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);
	}

	@Override
	public void mouseClicked(int x, int y) {
		return;
	}

	@Override
	public void mouseMoved(int x, int y) {
		bSubmit.setMouseOver(false);
		if (bSubmit.getBounds().contains(x, y))
			bSubmit.setMouseOver(true);

	}

	@Override
	public void mousePressed(int x, int y) throws IOException {
		if (bSubmit.getBounds().contains(x, y) && !bSubmit.disabled) {
			bSubmit.setMousePressed(true);
			int result=0;
			try {
				result = Integer.parseInt(textField.getText());
			}catch(NumberFormatException e) {
				textField.setText("Invalid number...");
				return;
			}
			bSubmit.disabled = true;
			ServerCommunication.out.writeObject(new Answer(result, question, Duration.between(receivedQuestionTime, LocalTime.now())));
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bSubmit.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onQuestionReceived(String question) {
		this.question = question;
		this.receivedQuestionTime = LocalTime.now();
		this.targetAnswerTime = receivedQuestionTime.plusSeconds(25);
		textField.setText("");
	}

	@Override
	public void receivedGameData(Game receivedObject) {
		this.game = receivedObject;
		bSubmit.disabled = false;
	}
}