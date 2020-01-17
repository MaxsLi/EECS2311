package views;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class IntroFrame extends JFrame {
	
	public static final int APP_WIDTH = 1750;
	public static final int APP_HEIGHT = 1000;
	public static final String APP_TITLE = "Venn Diagram Maker";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntroFrame window = new IntroFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public IntroFrame() {
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
		
		JButton createNewVenn = new JButton("Create New Venn Diagram");
		createNewVenn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		createNewVenn.setBounds(337, 360, 321, 122);
		getContentPane().add(createNewVenn);
		
		JButton getExistingVenn = new JButton("Get Existing Venn Diagram");
		getExistingVenn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		getExistingVenn.setBounds(1005, 360, 321, 122);
		getContentPane().add(getExistingVenn);
		
		//5. Show it
		this.setVisible(true);

	}
	
}
