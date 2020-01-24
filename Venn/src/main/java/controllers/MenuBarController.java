package controllers;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.WindowEvent;

public class MenuBarController {
	
	@FXML
	private void closeProgram(ActionEvent e) {
		MainApp.primaryStage.close();
	}
	
	//Method to close not using menuBar
	public static void closeProgram(WindowEvent e) {
		MainApp.primaryStage.close();
	}
}
