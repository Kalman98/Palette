package com.kalman98;

public class ArrayTile implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tile tile;
	int x, y;
	public ArrayTile(String type, int x, int y) {
		this.tile = new Tile(type);
		this.x = x;
		this.y = y;
	}
	
}
