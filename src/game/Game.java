package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;
	
	private BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	private BufferedImage floor = null;
		
	public int hp = 100;
	
	public Game() {
		new Window(1000,1000,"Wizard Game",this);
		start();
		
		//handler class handles a list of all GameObjects
		handler = new Handler();
		camera = new Camera(0,0);
		
		this.addKeyListener(new KeyInput(handler));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/game/level1.png");
		sprite_sheet = loader.loadImage("/game/sprite_sheet.png");
		
		ss = new SpriteSheet(sprite_sheet);
		
		floor = ss.grabImage(4, 2, 32, 32);
		
		this.addMouseListener(new MouseInput(handler,camera,ss));

		loadLevel(level);
		//handler.addObject(new Wizard(100,100,ID.Player, handler));
		//handler.addObject(new Box(100,100, ID.Block));
		handler.addObject(new FPSCounter(300,15,ID.FPS,ss,camera));

	}
	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	private void stop() {
		isRunning = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		for(int i = 0;i<handler.object.size();i++) {
			if(handler.object.get(i).getID() == ID.Player) { 
				camera.tick(handler.object.get(i));
			}
		}
		//handler is a list of all the GameObjects; everytime the game ticks; it calls all tick methods for all objects.
		handler.tick();
	}
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//convert graphics to graphics2d class
		Graphics2D g2d = (Graphics2D) g;

		//background
		//g.setColor(Color.black);
		//g.fillRect(0, 0, 1000, 1000);
		
		for ( int xx = 0;xx<  30 *72;xx+=32) {
			for(int yy=0;yy<30*72;yy+=32) {
				g.drawImage(floor, xx, yy, null);
			}
		}
		g2d.translate(-camera.getX(), -camera.getY());
		///////////////////////////////////
		
		handler.render(g);//must have this render below g fill background. show handle render above the background
		
		g2d.translate(camera.getX(), camera.getY());
		
		g.setColor(Color.red);
		g.fillRect(5, 5, 200, 32);
		g.setColor(Color.green);
		g.fillRect(5, 5, hp*2, 32);
		g.drawRect(5, 5, 200, 32);
		///////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	//loading the level
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		boolean temptrue = false;
		int enemies = 0;
		for (int xx=0;xx<w ; xx++) {
			for(int yy=0;yy<h;yy++) {
				int pixel = image.getRGB(xx,yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 & green == 255 & blue == 255)
					handler.addObject(new Block(xx*32,yy*32,ID.Block,ss));
				if(red==255 & blue == 0 & temptrue == false) {
					temptrue = true;
					handler.addObject(new Wizard(xx*32,yy*32,ID.Player,handler,this,ss));
				}
				if(green == 255 & red == 0 & enemies < 5) {
					enemies += 1;
					handler.addObject(new Goblin(xx*32,yy*32,ID.Goblin,handler,ss));
				}
			}
		}
	}
	public static void main(String args[]) {
		new Game();
	}
}
