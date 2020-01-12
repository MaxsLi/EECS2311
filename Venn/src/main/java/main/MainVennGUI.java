package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.Color;

public class MainVennGUI extends JFrame {
	public MainVennGUI() {
		getContentPane().setLayout(null);
		setBounds(200, 500, 1500, 800);
		JLabel mainTitle = new JLabel("Venn Diagram Maker");
		mainTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		mainTitle.setBounds(500,13,402,92);
		getContentPane().add(mainTitle);
		JButton btnRobbie=new JButton("Robbie Suwary");
		btnRobbie.setBounds(500, 150, 400, 40);
		getContentPane().add(btnRobbie);
//		Canvas canvas = new Canvas();
//		canvas.setBackground(Color.WHITE);
//		canvas.setBounds(238, 100, 676, 455);
//		getContentPane().add(canvas);
		setVisible(true);
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
