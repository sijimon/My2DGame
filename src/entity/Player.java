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
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		x = 100 ;
		y = 100 ;
		
		speed = 4;
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
		
		
		if (keyH.upPressed == true) {
			direction = "up";
			y -= speed;
		} else if (keyH.downPressed == true) {
			direction = "down";
			y += speed;
		} else if (keyH.leftPressed == true) {
			direction = "left";
			x -= speed;
		} else if (keyH.rightPressed == true) {
			direction = "right";
			x += speed;
		}
		spriteCounter ++;
		
		
		if(spriteCounter > 10 ) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}else if(spriteNum == 2) {
				spriteNum = 1;
			}
			
			spriteCounter = 0;
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
	//	g2.setColor(Color.WHITE);
	//	g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		
		BufferedImage image = null;

		switch (direction) {
		case "up":
				image = up1;
			break;
		case "down":
				image = down1;
			break;
		case "left":
				image = left1;
			break;
		case "right":
				image = right1;
			break;

		}
		
		
/*		
		switch (direction) {
		case "up":
			if (spriteCounter == 1) {
				image = up1;
			}
			if (spriteCounter == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteCounter == 1) {
				image = down1;
			}
			if (spriteCounter == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteCounter == 1) {
				image = left1;
			}
			if (spriteCounter == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteCounter == 1) {
				image = right1;
			}
			if (spriteCounter == 2) {
				image = right2;
			}
			break;

		}
*/		
		g2.drawImage(image, x, y,gp.tileSize,gp.tileSize,null);
		
	}
	
}
