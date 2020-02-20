package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Ellipse;
import javafx.stage.WindowEvent;

public class MenuBarController {
	
	@FXML
	private MenuItem aboutItem;

	public static List<Ellipse> circleList = new ArrayList<Ellipse>();
	// public static Group group = new Group();
	private final int C_X = 50, C_Y = 50;
	public static int circles = 0;
	private final int MAX_CIRCLES = 2;

	@FXML
	private void closeProgram(ActionEvent e) {
		// System.out.println("Closed properly.");
		MainApp.primaryStage.close();
	}

	// Method to close not using menuBar
	public static void closeProgram(WindowEvent e) {
		MainApp.primaryStage.close();
	}

	@FXML
	private void addCircle(ActionEvent e) {
		if (circles < MAX_CIRCLES) {
			Ellipse ellipse = new Ellipse(this.C_Y, this.C_Y);
			ellipse.setCenterX(100);
			ellipse.setCenterY(100);
			MenuBarController.circleList.add(ellipse);
			// group.getChildren().add(ellipse);
			circles++;
		}
	}
	
	public void openUserManual() {
		String currentDir = System.getProperty("user.dir");
		try {
		File userManual = new File(currentDir + "\\src\\main\\java\\resources\\Venn-UM.pdf");
		if(userManual.exists()) {
			if(Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().open(userManual);
				}
				catch(IOException e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("An Error Occurred");
					alert.setContentText("User Manual could not be opened.");
					alert.showAndWait();

				}
			}
			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("An Error Occurred");
				alert.setContentText("Desktop is Not Supported!");
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("An Error Occurred");
			alert.setContentText("User Manual Does not Exist!");
			alert.showAndWait();
		}
		}
		catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("An Error Occurred");
			alert.setContentText("User Manual could not be opened.");
			alert.showAndWait();

		}
	}

}
