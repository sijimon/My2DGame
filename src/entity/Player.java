package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;

	// Constants for player configuration
	private static final int DEFAULT_X = 100;
	private static final int DEFAULT_Y = 100;
	private static final int DEFAULT_SPEED = 4;
	private static final int SPRITE_ANIMATION_SPEED = 12;

	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {

		x = DEFAULT_X;
		y = DEFAULT_Y;

		speed = DEFAULT_SPEED;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	//Update get called 60 times per second
	public void update() {

		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {

			// Store current position
			int nextX = x;
			int nextY = y;

			// Calculate next position based on input
			if (keyH.upPressed == true) {
				direction = "up";
				nextY -= speed;
			} else if (keyH.downPressed == true) {
				direction = "down";
				nextY += speed;
			} else if (keyH.leftPressed == true) {
				direction = "left";
				nextX -= speed;
			} else if (keyH.rightPressed == true) {
				direction = "right";
				nextX += speed;
			}

			// Check screen boundaries
			boolean withinBounds = (nextX >= 0 && nextX <= gp.screenWidth - gp.tileSize &&
			                        nextY >= 0 && nextY <= gp.screenHeight - gp.tileSize);

			// Check tile collision at the four corners of the player sprite
			boolean collision = false;
			if (withinBounds) {
				// Check all four corners of the player hitbox
				boolean topLeft = gp.tileM.checkTileCollision(nextX, nextY);
				boolean topRight = gp.tileM.checkTileCollision(nextX + gp.tileSize - 1, nextY);
				boolean bottomLeft = gp.tileM.checkTileCollision(nextX, nextY + gp.tileSize - 1);
				boolean bottomRight = gp.tileM.checkTileCollision(nextX + gp.tileSize - 1, nextY + gp.tileSize - 1);

				collision = topLeft || topRight || bottomLeft || bottomRight;
			}

			// Only move if within bounds and no collision
			if (withinBounds && !collision) {
				x = nextX;
				y = nextY;
			}

			// Animate sprite
			spriteCounter++;
			if (spriteCounter > SPRITE_ANIMATION_SPEED) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;

		}
	
		g2.drawImage(image, x, y,gp.tileSize,gp.tileSize,null);
		
	}
	
}
