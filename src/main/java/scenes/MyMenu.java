package scenes;


import cargame.GameState;
import cargame.MessageListener;
import cargame.ServerCommunication;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import socket.object.Game;
import socket.object.Message;
import socket.object.MessageType;
import socket.object.Player;
import ui.MyButton;

import javax.swing.*;
import java.util.regex.*;
import java.awt.*;
import java.io.IOException;

import static cargame.Constants.WINDOW_WIDTH;
import static cargame.GameState.*;


@Component
public class MyMenu extends GameScene implements SceneMethods, MessageListener {

	private MyButton bPlaying, bQuit;

	@Autowired private JTextField textField;

	String tMessage = "Enter your in-game name:";

	public MyMenu() {
		initButtons();
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = WINDOW_WIDTH/2 - w/2;
		int y = 270;
		int yOffset = 70;
		bQuit = new MyButton("Quit", x, y + yOffset, w, h, false);
		bPlaying = new MyButton("Play", x, y , w, h, false);
	}

	@Override
	public void render(Graphics g) {
		drawButtons(g);
		drawErrorText(g);
	}

	public void drawErrorText(Graphics g) {
		Font defaultFont  = g.getFont();
		Font newFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);
		g.setFont(newFont);
		FontMetrics fontMetrics = g.getFontMetrics(newFont);
		g.setColor(Color.WHITE);
		int stringWidth = fontMetrics.stringWidth(tMessage);
		g.drawString(tMessage, WINDOW_WIDTH/2 - stringWidth/2, 200);
		g.setFont(defaultFont);
	}

	private void drawButtons(Graphics g) {
		if(!bPlaying.disabled) {
			bPlaying.draw(g);
		}
		bQuit.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		return;
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);

		bQuit.setMouseOver(false);

		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMouseOver(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMouseOver(true);

	}

	public boolean isValidNickname(String nickname) {
		String regex = "^[a-zA-Z0-9_]{1,10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(nickname);
		return matcher.matches();
	}

	@Override
	public void mousePressed(int x, int y) {
		System.out.println(textField.getText());
		if (bPlaying.getBounds().contains(x, y))
			try {
				String playerName = textField.getText();
				if(playerName.isEmpty()) {
					tMessage = "Please enter your name first!";
					return;
				}
				if(playerName.length() >= 10) {
					tMessage = "Your name must be no longer than 10";
					return;
				}
				if(!isValidNickname(playerName)) {
					tMessage = "Your name must composed by only ‘a’...’z’, ‘A’...’Z’, ‘0’...’9’, ‘_’";
					return;
				}
				bPlaying.disabled=true;
				ServerCommunication.connectToServer(new Player(playerName, 0));
				GameState.setPlayerName(playerName);
			} catch (IOException e) {
				bPlaying.disabled=false;
				tMessage = "Could not connect to server, please try again!";
			}
		else if (bQuit.getBounds().contains(x, y))
			System.exit(0);
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bPlaying.resetBooleans();
		bQuit.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageReceived(Message message) {
		//if receive start game Message packet, the game will start
		if(message.getType() == MessageType.START_GAME) {
			textField.setText("");
			GameState.SetGameState(PLAYING);
			resetMessage();
			bPlaying.disabled = false;
		}
		//if receive this message type, client need to register new name
		else if(message.getType() == MessageType.GAME_NAME_TAKEN) {
            try {
                ServerCommunication.closeConnections();
				tMessage = message.getMessage();
				bPlaying.disabled = false;
            } catch (IOException e) {
                System.out.println("Could not close connections");
            }
        }
		//waiting for enough players to join...
		else if(message.getType() == MessageType.WAIT_FOR_NEW_GAME) {
			tMessage = message.getMessage();
		}
	}

	private void resetMessage() {
		tMessage="Enter your in-game name:";;
	}

	public JTextField getTextField() {
		return textField;
	}
}