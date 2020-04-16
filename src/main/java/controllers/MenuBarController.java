package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.WindowEvent;
import views.MainApp;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MenuBarController {


	@FXML
	private MenuItem aboutItem;

	@FXML
	private Menu file;

	private ShapeSceneController shapeSceneCont;

	private MainApp mainApp;

	@FXML
	private MenuItem createNewVenn;

	@FXML
	private MenuItem openMenuItem;

	@FXML
	private MenuItem addCircleMenuItem;

	// Method to close not using menuBar
	public static void closeProgram(WindowEvent e) {
		MainApp.primaryStage.close();
	}

	@FXML
	private void createNew() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Create New Venn Diagram");
		alert.setContentText(
				"Any Unsaved Changes Will be lost. Are you Sure you would like to create a new Venn Diagram? ");

		ButtonType ok = new ButtonType("Ok");
		ButtonType cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(ok, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ok) {
			mainApp.switchScene("shapeScene", null);
		}
	}

	@FXML
	private void openExisting() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Open Existing Venn Diagram");
		alert.setContentText(
				"Any Unsaved Changes Will be lost. Are you sure you would like to open an Existing Venn Diagram?");

		ButtonType ok = new ButtonType("Ok");
		ButtonType cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(ok, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ok) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");

			File currentDir = new File(
					System.getProperty("user.home") + File.separator + "VennCreateFiles" + File.separator);

			if (!currentDir.exists()) {
				currentDir = new File(System.getProperty("user.home"));
			}

			fileChooser.setInitialDirectory(currentDir);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
			File selectedFile = fileChooser.showOpenDialog(null);

			if (selectedFile == null) {
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("Warning Dialog");
				alert1.setHeaderText("CSV Not Chosen");
				alert1.setContentText("Please Chose Correct CSV and try again");
				alert1.showAndWait();
			} else {
				mainApp.switchScene("load", selectedFile);
			}
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void saveProgram(ActionEvent e) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));

		shapeSceneCont = mainApp.getShapeSceneController();
		if (shapeSceneCont == null)
			System.out.println("LOL");
		if (shapeSceneCont != null) {
			try {
				shapeSceneCont.saveVenn(shapeSceneCont.getTextFields());
			} catch (NullPointerException NPE) {
				System.out.println("Thank You for Using Venn Create! (Exception)");
			}
		}
		e.consume();
	}

	@FXML
	private void addCircle() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));

		shapeSceneCont = mainApp.getShapeSceneController();
		shapeSceneCont.addCircle();
	}

}

