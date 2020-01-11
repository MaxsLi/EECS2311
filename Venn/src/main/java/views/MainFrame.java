package views;

import java.awt.Dimension;
import javax.swing.JFrame;
import controllers.Listener;

public class MainFrame extends JFrame {

	public static final int APP_WIDTH = 480;
	public static final int APP_HEIGHT = 640;
	public static final String APP_TITLE = "Venn Diagram";

	/**
	 * The action command to exit the application. 
	 */
	public static final String EXIT = "EXIT";


	public MainFrame(Listener listener) {
		//1. Create the frame
		super(APP_TITLE);

		//2. Choose what happens when the frame closes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame
		this.setJMenuBar(new MenuBar(listener));
		this.setContentPane(new DiagramPanel());

		//4. Size the frame
		this.setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
		// Set the start position relative to the centre of the screen
		this.setLocationRelativeTo(null);
		this.pack();
		
		//5. Show it
		this.setVisible(true);
	}

}