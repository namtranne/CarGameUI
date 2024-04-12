package cargame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import scenes.GameOver;
import scenes.MyMenu;
import scenes.Playing;

import javax.swing.*;
import java.awt.*;

@Component
public class Render {


    MyMenu menu;
    Playing playing;

    GameOver gameOver;

    @Autowired JTextField textField;

    @Autowired @Qualifier("fullSizeBackground")
    Image fullSizeBackground;

    @Autowired
    public Render(MyMenu menu, Playing playing, GameOver gameOver) {
        this.menu = menu;
        this.playing = playing;
        this.gameOver = gameOver;
    }

    public void render(Graphics g) {
        g.drawImage(fullSizeBackground, 0, 0, 840, 472, null);
        switch (GameState.gameState) {
            case MENU:
                textField.setVisible(true);
                menu.render(g);
                break;
            case PLAYING:
                textField.setVisible(true);
                playing.render(g);
                break;
            case GAME_OVER:
                textField.setVisible(false);
                gameOver.render(g);
                break;
            default:
                break;

        }

    }

}
