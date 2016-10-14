package com.kalman98;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player {

	int x, y, dX, dY, velX, velY, jumpTimer = 0, gravity = 0, up, down, left, right, jumpLength = 20, listCycle = 0;
	Rectangle gravRect = new Rectangle();
	String image;
	boolean jumpWait = false, onGround = false;
	ArrayList<String> tileList = new ArrayList<String>();
	ArrayList<String> clearTiles = new ArrayList<String>();
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.tileList.add("Red");
		this.tileList.add("Blue");
		this.tileList.add("Yellow");
		this.tileList.add("Orange");
		this.tileList.add("Purple");
		this.tileList.add("Green");
		this.tileList.add("Red_Spike");
		this.tileList.add("Blue_Spike");
		this.tileList.add("Yellow_Spike");
		this.tileList.add("Orange_Spike");
		this.tileList.add("Purple_Spike");
		this.tileList.add("Green_Spike");
		this.tileList.add("Grav_Down");
		this.tileList.add("Grav_Left");
		this.tileList.add("Grav_Up");
		this.tileList.add("Grav_Right");
		this.tileList.add("Black");
		this.tileList.add("Goal");
		
		this.image = "resources/Character/Player_2.png";
		this.setClear("Red");
	}
	
	public void update() {
		move();
	}
	
	
	public void move() {
		
		if (this.up == 1 && this.gravity == 0) {
			if (this.jumpWait == false) {
				this.velY = -1;
				this.jumpTimer += 2;
			} else this.velY = 1;
		} else if (this.up == 1) {
			this.velY = -1;
		}
		else if (this.up == 0 && this.gravity != 0) this.velY = 0;
		else if (this.up == 0 && this.gravity == 0) this.velY = 1;
		
		
		if (this.right == 1 && this.gravity == 1) {
			if (this.jumpWait == false) {
				this.velX = 1;
				this.jumpTimer += 2;
			} else this.velX = -1;
		} else if (this.right == 1) this.velX = 1;
		else if (this.right == 0 && this.gravity != 1) this.velX = 0;
		else if (this.right == 0 && this.gravity == 1) this.velX = -1;
		

		if (this.down == 1 && this.gravity == 2) {
			if (this.jumpWait == false) {
				this.velY = 1;
				this.jumpTimer += 2;
			} else this.velY = -1;
		} else if (this.down == 1) this.velY = 1;
		else if (this.down == 0 && this.gravity != 2 && this.gravity != 0 && this.up != 1) this.velY = 0;
		else if (this.down == 0 && this.gravity == 2) this.velY = -1;
		

		if (this.left == 1 && this.gravity == 3) {
			if (this.jumpWait == false) {
				this.velX = -1;
				this.jumpTimer += 2;
			} else this.velX = 1;
		} else if (this.left == 1) this.velX = -1;
		else if (this.left == 0 && this.gravity != 3 && this.gravity != 1 && this.right != 1) this.velX = 0;
		else if (this.left == 0 && this.gravity == 3) this.velX = 1;


		if (this.jumpTimer > this.jumpLength) this.jumpWait = true;
		if (this.jumpTimer > 0) {
			this.jumpTimer --;
		}
		
		
		checkCollisions();

	}
	
	public void checkCollisions() {
		
		
		
		ArrayList<Rectangle> tiles = new ArrayList<Rectangle>();
		for (int i = 0; i < GameFrame.TileArray.size(); i ++) {
			Rectangle rect = new Rectangle(GameFrame.TileArray.get(i).x * 16, GameFrame.TileArray.get(i).y * 16, 16, 16);
			tiles.add(rect);
		}
		
		Rectangle rect1 = new Rectangle(x + 8, y + 8, 1, 1);
		
		for (int i = 0; i < GameFrame.TileArray.size(); i ++) {
			if (rect1.intersects(tiles.get(i))) {
				updatePlayerWithColor(GameFrame.TileArray.get(i).tile.type);
			}
		}
		// gravity - 0/down 1/left 2/up 3/right
		
		if (gravity == 0) this.gravRect = new Rectangle(this.x, this.y + 1, this.getImage().getWidth(null), this.getImage().getHeight(null));
		if (gravity == 1) this.gravRect = new Rectangle(this.x -1, this.y, this.getImage().getWidth(null), this.getImage().getHeight(null));
		if (gravity == 2) this.gravRect = new Rectangle(this.x, this.y - 1, this.getImage().getWidth(null), this.getImage().getHeight(null));
		if (gravity == 3) this.gravRect = new Rectangle(this.x + 1, this.y, this.getImage().getWidth(null), this.getImage().getHeight(null));

		for (int i = 0; i < GameFrame.TileArray.size(); i ++) {
			boolean clear = false;
			for (int j = 0; j < clearTiles.size(); j ++) {
				if (GameFrame.TileArray.get(i).tile.type.contains(clearTiles.get(j))) clear = true;
			}
			if (!clear) {
				if (this.gravRect.intersects(tiles.get(i))) {
					
					jumpWait = false;
					jumpTimer = 0;
				} else if (gravity == 0 && up == 0) jumpWait = true;
				else if (gravity == 1 && right == 0) jumpWait = true;
				else if (gravity == 2 && down == 0) jumpWait = true;
				else if (gravity == 3 && left == 0) jumpWait = true;
			}
		}
		
		int mX = this.velX;
		int mY = this.velY;
		
		
		this.dX = (this.x + mX);
		this.dY = (this.y + mY);
		Rectangle boundsX = new Rectangle(this.dX,this.y, this.getImage().getWidth(null),this.getImage().getHeight(null));
		Rectangle boundsY = new Rectangle(this.x,this.dY, this.getImage().getWidth(null),this.getImage().getHeight(null));

		

		
		for (int i = 0; i < GameFrame.TileArray.size(); i ++) {
			boolean gravTouch = false;
			boolean goalTouch = false;
				if (GameFrame.TileArray.get(i).tile.type.contains("Grav")) {
					if (boundsX.intersects(tiles.get(i))) gravTouch = true;
					if (boundsY.intersects(tiles.get(i))) gravTouch = true;
					if (gravTouch) {
						if (GameFrame.TileArray.get(i).tile.type.contains("Down")) gravity = 0;
						if (GameFrame.TileArray.get(i).tile.type.contains("Left")) gravity = 1;
						if (GameFrame.TileArray.get(i).tile.type.contains("Up")) gravity = 2;
						if (GameFrame.TileArray.get(i).tile.type.contains("Right")) gravity = 3;
					}
				}
				if (GameFrame.TileArray.get(i).tile.type.contains("Goal")) {
					if (boundsX.intersects(tiles.get(i))) goalTouch = true;
					if (boundsY.intersects(tiles.get(i))) goalTouch = true;
					if (goalTouch) {
						GameFrame.party();
					}
				}
				boolean clear = false;
				boolean grav = false;
				for (int j = 0; j < clearTiles.size(); j ++) {
					if (GameFrame.TileArray.get(i).tile.type.contains(clearTiles.get(j))) clear = true;
					if (GameFrame.TileArray.get(i).tile.type.contains("Grav")) grav = true;
				}
				if (!clear && !grav) {
					if (boundsX.intersects(tiles.get(i))) mX = 0;
					if (boundsY.intersects(tiles.get(i))) mY = 0;
				}
		}
		
		
		
		
		for (int i = 0; i < GameFrame.TileArray.size(); i ++) {
			boolean spike = false;
			boolean clear = false;
			for (int j = 0; j < clearTiles.size(); j ++) {
				if (GameFrame.TileArray.get(i).tile.type.contains(clearTiles.get(j))) clear = true;
				//if (GameFrame.TileArray.get(i).tile.image.contains("Spike")) spike = true;
			}
			if (spike && !clear) {
				if (boundsX.intersects(tiles.get(i))) {
					GameFrame.resetLevel();
					break;
				}
				if (boundsY.intersects(tiles.get(i))) {
					GameFrame.resetLevel();
					break;
				}

			}
		}
		
		
		this.x += mX;
		this.y += mY;

	}
	
	public void draw(Graphics2D g2d) {
		if (!GameFrame.isInEditorMode()) g2d.drawImage(this.getImage(), this.x, this.y, null);
		else {
			g2d.setColor(Color.CYAN);
			g2d.drawRect(this.x * 16, this.y * 16, 16, 16);
		}
	}
	
	public Image getImage() {
		ImageIcon ic;
		if (!GameFrame.isInEditorMode()) ic = new ImageIcon(this.image);
		else ic = new ImageIcon("resources/Tiles/" + this.tileList.get(listCycle) + ".png");
		return ic.getImage();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_I || key == KeyEvent.VK_C) GameFrame.toggleEditor();
		
		if (!GameFrame.isInEditorMode()) {
			if (key == KeyEvent.VK_D || key == KeyEvent.VK_E) this.right = 1;
			if (key == KeyEvent.VK_A || key == KeyEvent.VK_A) this.left = 1;
			if (key == KeyEvent.VK_W || key == KeyEvent.VK_COMMA) this.up = 1;
			if (key == KeyEvent.VK_S || key == KeyEvent.VK_O) this.down = 1;
			if (key == KeyEvent.VK_R || key == KeyEvent.VK_P) GameFrame.resetLevel();
		} else {
			if (key == KeyEvent.VK_E) this.x ++ ;
			if (key == KeyEvent.VK_A) this.x --;
			if (key == KeyEvent.VK_COMMA) this.y --;
			if (key == KeyEvent.VK_O) this.y ++;
			if (key == KeyEvent.VK_SPACE) this.setTile();
			if (key == KeyEvent.VK_RIGHT) this.listCycle ++;
			if (key == KeyEvent.VK_LEFT) this.listCycle --;
			if (key == KeyEvent.VK_BACK_SPACE) this.removeTile();
			if (key == KeyEvent.VK_ENTER) GameFrame.saveLevel();	
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_E) this.right = 0;
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_A) this.left = 0;
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_COMMA) this.up = 0;
		if (key == KeyEvent.VK_S || key == KeyEvent.VK_O) this.down = 0;
	}
	
	
	public void setClear(String clear1) {
		clearTiles.clear();
		clearTiles.add(clear1);
	}
	public void setClear(String clear1, String clear2) {
		clearTiles.clear();
		clearTiles.add(clear1);
		clearTiles.add(clear2);
	}
	public void setClear(String clear1, String clear2, String clear3) {
		clearTiles.clear();
		clearTiles.add(clear1);
		clearTiles.add(clear2);
		clearTiles.add(clear3);
	}
	
	public void updatePlayerWithColor(String color) {

		if (color.contains("Red")) setClear("Red", "Orange", "Purple");
		if (color.contains("Blue")) setClear("Blue", "Green", "Purple");
		if (color.contains("Yellow")) setClear("Yellow", "Green", "Orange");
		if (color.contains("Orange")) setClear("Orange", "Yellow", "Red");
		if (color.contains("Purple")) setClear("Purple", "Red", "Blue");
		if (color.contains("Green")) setClear("Green", "Yellow", "Blue");
		if (color.contains("Chartruse")) System.out.println("Whaaaaaaa? Chartruse isn't even a valid color!");
		
	}
	
	public void teleport(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setTile() {
		this.removeTile();
		ArrayTile aTile = new ArrayTile(tileList.get(listCycle), this.x, this.y);
		GameFrame.TileArray.add(aTile);
	}
	
	public void removeTile() {
		for (int i = 0; i < GameFrame.TileArray.size(); i ++) {
			if (GameFrame.TileArray.get(i).x == this.x && GameFrame.TileArray.get(i).y == this.y)
				GameFrame.TileArray.remove(i);
		}
	}
}
