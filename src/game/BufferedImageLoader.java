package game;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class BufferedImageLoader {
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch(IOException e) {
			System.out.println("Had an issue loading level..");
			e.printStackTrace();
		}
		return image;
	}
}
