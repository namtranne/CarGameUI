package cargame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import scenes.MyMenu;
import scenes.Playing;

import javax.swing.*;


@SpringBootApplication
@ComponentScan({"scenes", "cargame", "input"})
public class CarGameApplication extends JFrame  implements CommandLineRunner {

	@Autowired
	private GameScreen gameScreen;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	// Classes
	@Autowired
	private Render render;

	@Autowired
	private Playing playing;


	@Autowired
	public CarGameApplication(GameScreen gameScreen, JTextField textField) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Your Game");
		gameScreen.add(textField);
		add(gameScreen);
		pack();
		setVisible(true);
	}


	private void updateGame() {
		switch (GameState.gameState) {
			case MENU:
				break;
			case PLAYING:
				playing.update();
				break;
			default:
				break;
		}
	}

	public static void main(String[] args) {

		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(CarGameApplication.class)
				.web(WebApplicationType.NONE)
				.headless(false)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// Render
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}

			// Update
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}
		}
	}

}
