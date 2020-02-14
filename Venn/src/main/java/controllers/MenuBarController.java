package controllers;

import java.util.ArrayList;
import java.util.List;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Ellipse;
import javafx.stage.WindowEvent;

public class MenuBarController {

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

}
