package scenes;

import java.awt.*;
import java.io.IOException;

public interface SceneMethods {

	public void render(Graphics g);

	public void mouseClicked(int x, int y) throws IOException;

	public void mouseMoved(int x, int y);

	public void mousePressed(int x, int y) throws IOException;

	public void mouseReleased(int x, int y);

	public void mouseDragged(int x, int y);

}
