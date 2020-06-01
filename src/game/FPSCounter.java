package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class FPSCounter extends GameObject{
	protected double oldTime;
	Camera camera;
	public FPSCounter(int x, int y,ID id, SpriteSheet ss, Camera camera) {
		super(x,y,id,ss);
		this.camera = camera;
		this.oldTime = System.nanoTime();
	}
	public void tick() {
		x = (int) camera.getX() + 250;
		y = (int) camera.getY() + 25;
	}
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.drawString("FPS:"+getFPS(oldTime), x, y);
	}
	public Rectangle getBounds() {
		return null;
	}
	public double getFPS(double oldTime) {
		double newTime = System.nanoTime();
		double delta = newTime - oldTime;
		double FPS = 1/(delta * 1000);
		oldTime = newTime;
		return FPS;
	}
}
