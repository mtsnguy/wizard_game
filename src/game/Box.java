package game;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class Box extends GameObject{
	public Box(int x, int y, ID id, SpriteSheet ss) {
		super(x,y, id,ss);
		velX = 1; //place f after number because compiler will treat is as double literal by default
	}
//Typically a tick is an iteration of some loop, such as the main game logic loop. 
//One can say, for example, that the game logic "ticks"
//once a frame, or that "during the tick, the character positions are updated."
	public void tick() {
		x += velX;
		y += velY;
	}
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 32, 32);
	}
	public Rectangle getBounds() {
		return null;
	}
	
}
