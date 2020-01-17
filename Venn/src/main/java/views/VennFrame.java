package views;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;

public class VennFrame extends JFrame {
	
	
	public static final int APP_WIDTH = 1750;
	public static final int APP_HEIGHT = 1000;
	public static final String APP_TITLE = "Venn Diagram Maker";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VennFrame window = new VennFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VennFrame() {
		//1. Create the frame
				super(APP_TITLE);

		//2. Choose what happens when the frame closes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//3. Add componenets to the frame
		
		
		//4. Size the frame
		this.setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
		
		// Set the start position relative to the centre of the screen
		this.setLocationRelativeTo(null);
		this.pack();
		getContentPane().setLayout(null);
		
		Canvas canvas = new Canvas();
		
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(303, 133, 1174, 679);
		getContentPane().add(canvas);
		
		
		
		
		//5. Show it
		this.setVisible(true);

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawOval(50, 50, 100, 100);;
	}
}
