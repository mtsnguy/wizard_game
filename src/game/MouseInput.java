package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;
	
	public MouseInput (Handler handler , Camera camera,SpriteSheet ss) {
		this.handler = handler;
		this.camera = camera;
		this.ss = ss;
	}
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		
		for ( int i = 0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Player) {
				handler.addObject(new Projectile(tempObject.getX() + 16 , tempObject.getY() + 24,ID.Projectile,handler,mx,my,ss));
			}
		}
	}
}
