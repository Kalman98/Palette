package com.kalman98;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Tile implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	String image;
	public Tile(String type) {
		this.image = ("resources/Tiles/" + type + ".png");
		this.type = type;
	}
	
	public Image getImage() {
		ImageIcon ic = new ImageIcon(this.image);
		return ic.getImage();
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
}
