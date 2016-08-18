package com.kalman98;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final JFrame frame = new JFrame("Ludare 31 - Palette");
	
	public static void main(String[] args) {
		
		frame.setSize(800, 640);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		JComponent contentPane = new GameFrame();
		contentPane.setOpaque(true);
		frame.setContentPane(contentPane);
		
	}

}
