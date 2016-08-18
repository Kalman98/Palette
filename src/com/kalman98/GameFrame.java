package com.kalman98;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	static Player player = new Player(0, 580);
	Timer mainTimer;
	static ArrayList<ArrayTile> TileArray = new ArrayList<ArrayTile>();
	static boolean won = false;
	public GameFrame() {
		setFocusable(true);
		setBackground(Color.LIGHT_GRAY);
		addKeyListener(new KeyAdapt(player));
		
		resetLevel();
		
		mainTimer = new Timer(10, this);
		mainTimer.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		player.draw(g2d);
		
		for (int i = 0; i < TileArray.size(); i ++) {
			for (int j = 0; j < player.clearTiles.size(); j ++) {
				if (TileArray.get(i).tile.type.contains(player.clearTiles.get(j))) {
					TileArray.get(i).tile.image = ("resources/Tiles/" + TileArray.get(i).tile.type + "_Clear.png");
					break;
				} else TileArray.get(i).tile.image = ("resources/Tiles/" + TileArray.get(i).tile.type + ".png");
			
			}
			g2d.drawImage(TileArray.get(i).tile.getImage(), TileArray.get(i).x * 16, TileArray.get(i).y * 16, 16, 16, null);
		}
		
		if (won) {
			g2d.setColor(Color.CYAN);
			Font winFont = new Font("BOLD", 50, 50);
			g2d.setFont(winFont);
			g2d.drawString("You WON!!!!?!?!?!?!?!?!", Main.frame.getWidth() / 2, Main.frame.getHeight() / 3);
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
		repaint();
		player.update();
		
	}
	
	public Tile getTileByID(int xPos, int yPos) {
		
		for(;yPos > 1;) {
			yPos -= 1;
			xPos += 50;
		}
		
		return TileArray.get(xPos).tile;

		
	}
	
	public int getTileID(int x, int y) {
		
		for(;y > 0;) {
			y -= 1;
			x += 50;
		}
		System.out.println("Tile is at " + x);
		return x;
	}
	
	public Point getTileCoordFromID(int id) {
		int xPos = id, yPos = 0;
		for(; xPos > 50;) {
			xPos -= 50;
			yPos += 1;
		}
		return new Point(xPos, yPos);
	}
	
	public static void setTile(int x, int y, String type) {
		

		ArrayTile aTile = new ArrayTile(type, x, y);
		TileArray.add(aTile);
	}
	
	
	public static void resetLevel() {
		player.setClear("Red");
		player.teleport(0, 580);
		player.gravity=0;
		loadLevel();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadLevel() {
		try {
			FileInputStream fileIn = new FileInputStream("resources/Levels/level.pal");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			TileArray = (ArrayList<ArrayTile>) in.readObject();
			in.close();
			fileIn.close();
			System.out.println("FINISHED!");
		}catch(IOException i) {
			System.out.println("Something bad happened when loading level.pal.");
			i.printStackTrace();
		}catch(ClassNotFoundException c) {
			System.out.println("Class ArrayTile was not found.");
			c.printStackTrace();
		}
	}
	
	
	public static void Party() {
		won = true;
	}
	
}




/* Code for the level used to test this

TileArray.clear();
setTile(0, 0, "Iron_Bars");
setTile(5, 5, "Iron_Bars");
setTile(10, 20, "Iron_Bars");
setTile(25, 25, "Red");
setTile(26, 24, "Red");
setTile(0, 25, "Red");
setTile(1, 25, "Red");
setTile(2, 25, "Red");
setTile(3, 25, "Red");
setTile(4, 25, "Red");
setTile(5, 25, "Red");
setTile(6, 25, "Red");
setTile(40, 25, "Blue");
setTile(25, 15, "Yellow");
setTile(40, 37, "Purple");
setTile(25, 26, "Blue");
setTile(23, 28, "Blue");
setTile(24, 28, "Blue");
setTile(25, 28, "Blue");
setTile(26, 28, "Blue");
setTile(24, 27, "Red");
setTile(24, 26, "Red");
setTile(26, 31, "Yellow");
setTile(28, 31, "Yellow");
setTile(28, 30, "Blue");
setTile(28, 29, "Red");
setTile(29, 29, "Orange");
setTile(30, 29, "Yellow");
setTile(31, 29, "Green");
setTile(29, 30, "Black");
setTile(30, 30, "Black");
setTile(31, 30, "Black");
setTile(22, 27, "Red_Spike");
setTile(29, 27, "Blue_Spike");
setTile(25, 30, "Yellow_Spike");
setTile(31, 27, "Grav_Right");
setTile(40, 27, "Grav_Left");
setTile(15, 15, "Grav_Down");
setTile(20, 36, "Grav_Up");
setTile(49, 39, "Orange");
setTile(10, 30, "Orange_Spike");
setTile(17, 9, "Purple_Spike");
setTile(11, 35, "Green_Spike");*/