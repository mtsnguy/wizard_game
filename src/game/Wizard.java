package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject {
	
	Handler handler;
	Game game;
	private BufferedImage wizard_image;
	
	public Wizard(int x, int y, ID id , Handler handler,Game game, SpriteSheet ss) {
		super(x,y,id,ss);
		this.handler = handler;
		this.game = game;
		wizard_image = ss.grabImage(1, 1, 32, 48);
	}
	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		
		//movement 
		if(handler.isUp()) velY = -10;
		else if(!handler.isDown()) velY = 0;
		
		if(handler.isDown()) velY = 10;
		else if (!handler.isUp()) velY = 0;
		
		if(handler.isRight()) velX = 10;
		else if (!handler.isLeft()) velX = 0;
		
		if(handler.isLeft()) velX = -10;
		else if (!handler.isRight()) velX = 0;

	}
	public void collision() {
		for ( int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())){
					
					x += velX * -1;
					y += velY * -1;
				}
			}
			if(tempObject.getID() == ID.Goblin) {
				
				if(getBounds().intersects(tempObject.getBounds())){
					game.hp -= 2;
				}
			}
		}
	}
	public void render(Graphics g) {
		g.drawImage(wizard_image,x,y,null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}

}
