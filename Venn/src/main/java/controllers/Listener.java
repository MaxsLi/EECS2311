package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import models.VennSet;
import views.MainFrame;

public class Listener implements KeyListener, ActionListener {
	//Merge test
	//Commit
	//New Change
	private MainFrame mainFrame;
	
	/**
	 * Initializes the controller so that it has no model
	 * and no view.
	 */
	public Listener() {
		this.mainFrame = null;
	}

	/**
	 * Set the model and view for this controller.
	 * 
	 * @param vennSet the model
	 * @param mainFrame the view
	 */
	public void set(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * Respond to the user clicking a button or menu item in
	 * the view, i.e. main frame.
	 * 
	 * @param e the action event to respond to
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
			case MainFrame.ADD_CIRCLE:
				VennSet vennSet = new VennSet();
				this.mainFrame.addCircle(vennSet);
				break;

			case MainFrame.EXIT:
				this.mainFrame.dispose();
				break;

			case MainFrame.RESET_DIAGRAM:
				this.mainFrame.resetDiagram();
				break;

			default:
				break;
		}
	}
	
	@Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}