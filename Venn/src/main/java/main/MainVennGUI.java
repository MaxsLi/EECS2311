package main;


import javax.swing.JFrame;
import javax.swing.JLabel;


import java.awt.Font;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JButton;

public class MainVennGUI extends JFrame {
	
	
	public MainVennGUI() {
		getContentPane().setLayout(null);
		setBounds(1000,1000,1245,731);
		setLocationRelativeTo(null);
		
		JLabel mainTitle = new JLabel("Venn Diagram Maker");
		mainTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		mainTitle.setBounds(446, 13, 402, 92);
		getContentPane().add(mainTitle);
		
		JButton btnNewButton = new JButton("Chidalu Agbakwa Test");
		btnNewButton.setFont(new Font("Sylfaen", Font.PLAIN, 25));
		btnNewButton.setBounds(95, 105, 307, 80);
		getContentPane().add(btnNewButton);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame window = new MainVennGUI();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
