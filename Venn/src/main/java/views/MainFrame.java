package views;

import java.awt.Dimension;

import javax.swing.JFrame;
import controllers.Listener;
import models.VennSet;
public class MainFrame extends JFrame {
	//THis is a comment

	public static final int APP_WIDTH = 320;
	public static final int APP_HEIGHT = 320;
	public static final String APP_TITLE = "Venn Diagram Maker";

	private DiagramPanel diagramPanel;

	/**
	 * The action command to add a circle to user's Venn diagram 
	 */
	public static final String ADD_CIRCLE = "ADD_CIRCLE";

		/**
	 * The action command to reset and clear all the circles in user's Venn diagram 
	 */
	public static final String RESET_DIAGRAM = "RESET_DIAGRAM";

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
		diagramPanel = new DiagramPanel();
		this.setContentPane(diagramPanel);

		//4. Size the frame
		this.setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
		// Set the start position relative to the centre of the screen
		this.setLocationRelativeTo(null);
		this.pack();
		
		//5. Show it
		this.setVisible(true);
	}

	public void addCircle(VennSet vennSet) {
		this.diagramPanel.addCircle(vennSet);
	}

	public void resetDiagram() {
		this.diagramPanel.resetDiagram();
	}
}