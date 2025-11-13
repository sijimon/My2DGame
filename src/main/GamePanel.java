package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16X16 tile

	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48 X 48 tile
	public final int maxScreenCol = 24;
	public final int maxScreenRow = 18;

	public final int screenWidth = tileSize * maxScreenCol; // 48 X 24 = 1152 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 48 X 18 = 864 pixels

	// FPS frames per second
	int FPS = 60;

	public TileManager  tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	Player player = new Player(this, keyH);

	// Level system
	public int currentLevel = 3;
	final int maxLevel = 5;
	boolean advanceKeyReady = true; // prevents holding Enter from triggering multiple advances


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

	player.update();

	// Handle level advance on Enter key (single press)
	if (keyH.enterPressed && advanceKeyReady) {
		advanceKeyReady = false; // consume until key released
		nextLevel();
	}
	if (!keyH.enterPressed) {
		advanceKeyReady = true;
	}
	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// draw map and player
		tileM.draw(g2);
		player.draw(g2);

		// draw level indicator
		g2.setColor(Color.WHITE);
		g2.drawString("Level: " + currentLevel, 10, 20);
		g2.dispose(); // good practice to save some memory
		

	}

	// Advance to the next level. Wraps back to 1 after maxLevel.
	private void nextLevel() {
		if (currentLevel < maxLevel) {
			currentLevel++;
		} else {
			currentLevel = 1; // wrap to start
		}
		String filePath = String.format("/maps/map%02d.txt", currentLevel);
		System.out.println("Advancing to level " + filePath);
		tileM.loadMap(filePath);
		// reset player position for the new level
		player.setDefaultValues();
		System.out.println("Loaded level " + currentLevel + " (" + filePath + ")");
	}

}