package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Goblin extends GameObject{
	private Handler handler;
	private BufferedImage goblin_image;
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	
	public Goblin(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		
		goblin_image = ss.grabImage(4,1, 32, 32);
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;	
		
		choose = r.nextInt(100);
		
		for (int i = 0;i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Block) {
				if(getBoundsBig().intersects(tempObject.getBounds())) {
					// multiplying by -1 , inverts the velocity of the object
					x += (velX * 2) * -1;
					y += (velY * 2) * -1;
					velX *= -1;
					velY *= -1;
				}else if(choose == 0){
					velX = (r.nextInt(4 - -4) + -4);
					velY = (r.nextInt(4 - -4) + -4);
				}
			}
			if(tempObject.getID() == ID.Projectile) {
				if(getBounds().intersects(tempObject.getBounds())) {
					hp -= 25;
					handler.removeObject(tempObject);
				}
			}
		}
		if(hp <= 0) {
			System.out.println("Killed");
			handler.removeObject(this);
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(goblin_image, x, y, null);
		//g2d.draw(getBoundsBig());
		
		g.setColor(Color.red);
		g.fillRect(x-8, y-10, 50, 8);
		g.setColor(Color.green);
		g.fillRect(x-8, y-10, hp/2, 8);
	}
	public Rectangle getBoundsBig() {
		return new Rectangle(x - 8, y-8,48,48);
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}

}
