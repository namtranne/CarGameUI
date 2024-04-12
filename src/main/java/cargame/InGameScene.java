package cargame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;

//@Component
public class InGameScene {
    JFrame fr;
    JPanel carPanel;

    JLabel background;


    public void moveCar() {
        int newX = carPanel.getX() + 10; // Adjust this value according to how much you want to move the car
        int newY = carPanel.getY(); // Maintain the current Y position
        carPanel.setBounds(newX, newY, 60, 30);
    }


    @Autowired
    public void setCarPanel( @Qualifier("carLabel") JLabel carLabel) {
        this.carPanel = new JPanel();
        carPanel.setLayout(null);
        carPanel.add(carLabel);
        carPanel.setOpaque(false);
        carPanel.setSize(60, 30);
    }


    @Autowired
    public void setFr( @Qualifier("background") JLabel background) {
        this.background = background;
        fr = new JFrame("Car Game");
        fr.setLayout(null);
        fr.add(carPanel);
        fr.setSize(840, 180);
        fr.add(background);
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setVisible(boolean b) {
        fr.setVisible(b);
    }
}
