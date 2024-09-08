package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Currency;

import javax.swing.JPanel;

import entity.Player;


public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16X16 tile

	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48 X 48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;

	final int screenWidth = tileSize * maxScreenCol; // 48 X16 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 48 X 12 576 pixels

	// FPS frames per second
	int FPS = 60;

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	Player player = new Player(this, keyH);

	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();

	}

	/*
	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.0166 second interval
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {

			// System.out.println("The Game loop is running..");
			long currentTime = System.nanoTime();

			// 1. UPDATE : update informations such as character positions
			update();

			// 2. Draw: draw the screen with the updated information
			repaint();

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000 ;
				
				if(remainingTime < 0) {
					remainingTime = 0 ;
				}
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
*/
	
	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.0166 second interval
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		//check FPS
		long timer = 0;
		int drawCount = 0; 
		

		while (gameThread != null) {

			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer+= (currentTime -  lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS:"+drawCount);
				drawCount =0;
				timer = 0;
			}

		}

	}
	

	public void update() {


  // moved to Player component

		/*
		if (keyH.upPressed == true) {
			playerY -= playerSpeed;
		} else if (keyH.downPressed == true) {
			playerY += playerSpeed;
		} else if (keyH.leftPressed == true) {
			playerX -= playerSpeed;
		} else if (keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
*/

	player.update();
	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
// moved tp player component
//		g2.setColor(Color.WHITE);
//		g2.fillRect(playerX, playerY, tileSize, tileSize);
		player.draw(g2);
		g2.dispose(); // good practice to same some memory
		

	}

}
