package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.Color;

public class MainVennGUI extends JFrame {
	public MainVennGUI() {
		getContentPane().setLayout(null);
		
		JLabel mainTitle = new JLabel("Venn Diagram Maker");
		mainTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		mainTitle.setBounds(446, 13, 402, 92);
		getContentPane().add(mainTitle);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(238, 100, 676, 455);
		getContentPane().add(canvas);
	}
	
	public static void main(String[] args) {
		try {
			JFrame test = new MainVennGUI();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
