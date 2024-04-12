package scenes;


import cargame.ServerCommunication;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;

public class GameScene {

	protected int animationIndex;
	protected int ANIMATION_SPEED = 25;
	protected int tick;



	protected void updateTick() {
		tick++;
		if (tick >= ANIMATION_SPEED) {
			tick = 0;
			animationIndex++;
			if (animationIndex >= 4)
				animationIndex = 0;
		}
	}
}
