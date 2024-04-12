package cargame;

import input.KeyboardListener;
import input.MyMouseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static cargame.Constants.WINDOW_HEIGHT;
import static cargame.Constants.WINDOW_WIDTH;

@Component
public class GameScreen extends JPanel {

    @Autowired
    private Render render;
    private Dimension size;

    private KeyboardListener keyboardListener;

    public GameScreen() {
        setPanelSize();
        setLayout(null);
    }

    @Autowired
    public void initInputs(MyMouseListener myMouseListener) {
        keyboardListener = new KeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setPanelSize() {
        size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render.render(g);

    }

}