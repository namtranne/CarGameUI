package cargame;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

import static cargame.Constants.WINDOW_HEIGHT;
import static cargame.Constants.WINDOW_WIDTH;

@Configuration
public class CarGameGUIConfig {


    @Bean
    public Image carImage() {
        ImageIcon carIcon = new ImageIcon("src/main/resources/car.png");
        Image carImage = carIcon.getImage().getScaledInstance(60, 30, Image.SCALE_SMOOTH);
        return carImage;
    }

    @Bean
    public Image backgroundImage() {
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/background.png");
        Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(840, 150, Image.SCALE_SMOOTH);
        return scaledBackgroundImage;
    }

    @Bean
    public Image fullSizeBackground() {
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/FullSizeBackground.jpg");
        Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(840, 472, Image.SCALE_SMOOTH);
        return scaledBackgroundImage;
    }

    @Bean
    public JTextField textField() {
        JTextField textField = new JTextField(16);
        int width = 300;
        int height = 50;
        int x = WINDOW_WIDTH/2 - width/2;
        int y = WINDOW_HEIGHT/2 - height/2;
        textField.setBounds(x, y, width, height);
        return textField;
    }
}
