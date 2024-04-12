package input;

import cargame.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scenes.GameOver;
import scenes.MyMenu;
import scenes.Playing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

@Component
public class MyMouseListener implements MouseListener, MouseMotionListener {

    @Autowired
    MyMenu menu;

    @Autowired
    Playing playing;

    @Autowired
    GameOver gameOver;


    @Override
    public void mouseDragged(MouseEvent e) {
        return;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                menu.mouseMoved(e.getX(), e.getY());
                break;
            case PLAYING:
                playing.mouseMoved(e.getX(), e.getY());
                break;
            case GAME_OVER:
                gameOver.mouseMoved(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameState.gameState) {
                case MENU:
                    menu.mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    playing.mouseClicked(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    gameOver.mouseClicked(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                menu.mousePressed(e.getX(), e.getY());
                break;
            case PLAYING:
                try {
                    playing.mousePressed(e.getX(), e.getY());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case GAME_OVER:
                gameOver.mousePressed(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                menu.mouseReleased(e.getX(), e.getY());
                break;
            case PLAYING:
                playing.mouseReleased(e.getX(), e.getY());
                break;
            case GAME_OVER:
                gameOver.mouseReleased(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // We wont use this

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // We wont use this
    }

}