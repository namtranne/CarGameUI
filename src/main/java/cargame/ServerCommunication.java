package cargame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scenes.GameOver;
import scenes.MyMenu;
import scenes.Playing;
import socket.object.*;

import java.io.*;
import java.net.Socket;

@Component
public class ServerCommunication {
    @Autowired
    public ServerCommunication(Playing playing, MyMenu menu, GameOver gameOver) {
        ServerCommunication.playing = playing;
        ServerCommunication.menu = menu;
        ServerCommunication.gameOver = gameOver;
    }

    private static final String SERVER_ADDRESS = "localhost"; // Change this to your server's address
    private static final int SERVER_PORT = 12345; // Change this to your server's port

    public static Socket socket;

    private static Playing playing;

    private static MyMenu menu;

    private static GameOver gameOver;

    public static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void connectToServer(Player player) throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(player);
        in = new ObjectInputStream(socket.getInputStream());
        Thread socketThread = new Thread(ServerCommunication::run);
        socketThread.start();
    }

    public static void run() {
        try {
            while (true) {
                if(socket.isClosed()) {
                   return;
                }
                Object receivedObject = in.readObject();

                //The Message packets are passed to menu, they are start game messages
                //duplicate name messages and so on
                if(receivedObject instanceof Message && menu != null) {
                    menu.onMessageReceived((Message) receivedObject);
                }

                //if the Question packet is received
                // it will pass the question to playing scene
                if (receivedObject instanceof Question && playing != null) {
                    playing.onQuestionReceived(((Question) receivedObject).getQuestionText());
                }

                //if the Game packet is received
                // it will also pass to the playing scene to update the game
                if(receivedObject instanceof Game && playing != null) {
                    playing.receivedGameData((Game)receivedObject);
                }

                //when the client receive GameResult packet
                // it will pass them to GameOver scene to display the result
                if(receivedObject instanceof GameResult && gameOver != null) {
                    gameOver.onGameResultReceived((GameResult) receivedObject);
                    closeConnections();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            run();
        }
    }

    public static void closeConnections() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

    public void close() {
        try {
            // Close the socket connection
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

