package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile extends GameObject{
	private Handler handler;
	
	public Projectile(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		
		velX = (mx - x) / 50;
		velY = (my - y) / 50;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		for( int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getID() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillOval(x, y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,8,8);
	}
	
}
