package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import views.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuBarController {
	
	@FXML
	private MenuItem aboutItem;
	
	private ShapeSceneController shapeSceneCont;

	private MainApp mainApp;
	
	@FXML
	private MenuItem createNewVenn;
	
	@FXML
	private MenuItem openMenuItem;
	
	@FXML
	private MenuItem addCircleMenuItem;
	
	/* Undo/redo
	@FXML
	private MenuItem undoBtn;
	
	@FXML
	private MenuItem redoBtn;
	public void addKeyShortcuts() {
		// mainApp.primaryStage.getScene().setOnKeyPressed(e->{
		// if (e.isControlDown()&&e.getCode()==KeyCode.Z) {
		// shapeSceneCont=mainApp.getShapeSceneController();
		// shapeSceneCont.undo();
		// }
		// else if (e.isControlDown()&&e.isShiftDown()&&e.getCode()==KeyCode.Y) {
		// shapeSceneCont=mainApp.getShapeSceneController();
		// shapeSceneCont.redo();
		// }
		// });
		undoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
		redoBtn.setAccelerator(
				new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.CONTROL_DOWN));
	}
	@FXML
	private void undo(ActionEvent e) {
		shapeSceneCont = mainApp.getShapeSceneController();
		// shapeSceneCont.undo();
	}
	@FXML
	private void redo(ActionEvent e) {
		shapeSceneCont = mainApp.getShapeSceneController();
		// shapeSceneCont.redo();
	}
	*/
	
	
	@FXML
	private void createNew() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Create New Venn Diagram");
		alert.setContentText("Any Unsaved Changes Will be lost. Are you Sure you would like to create a new Venn Diagram? ");

		ButtonType ok = new ButtonType("Ok");
		ButtonType cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(ok, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ok) {
			mainApp.switchScene("shapeScene", null);
		} else {
			return;
		}
	}
	
	@FXML
	private void openExisting() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Open Existing Venn Diagram");
		alert.setContentText("Any Unsaved Changes Will be lost. Are you sure you would like to open an Existing Venn Diagram?");

		ButtonType ok = new ButtonType("Ok");
		ButtonType cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(ok, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ok) {
			 FileChooser fileChooser = new FileChooser();
			 fileChooser.setTitle("Open Resource File");
			 
			 
			 File currentDir = new File(System.getProperty("user.home") + File.separator + "VennCreateFiles" + File.separator);
			 
			 if( ! currentDir.exists()) {
				 currentDir = new File(System.getProperty("user.home"));
			 }	 
			 
			 fileChooser.setInitialDirectory(currentDir);
			 fileChooser.getExtensionFilters().addAll(
			         new ExtensionFilter("CSV Files", "*.csv")
			 );
			 File selectedFile = fileChooser.showOpenDialog(null);
			 
			 
			 
			 
			 if(selectedFile == null) {
					Alert alert1 = new Alert(AlertType.WARNING);
					alert1.setTitle("Warning Dialog");
					alert1.setHeaderText("CSV Not Chosen");
					alert1.setContentText("Please Chose Correct CSV and try again");
					alert1.showAndWait();
			 }
			 else {
				 mainApp.switchScene("load", selectedFile);
			 }
		} else {
			return;
		}
	}

	// Method to close not using menuBar
	public static void closeProgram(WindowEvent e) {
		MainApp.primaryStage.close();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}
	
	public void saveProgram(ActionEvent e) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));
		
		shapeSceneCont = mainApp.getShapeSceneController();
		if(shapeSceneCont == null) System.out.println("LOL");
		if (shapeSceneCont != null) {
			try {
			shapeSceneCont.saveVenn(shapeSceneCont.getTextFields());
			}
			catch(NullPointerException NPE) {
				System.out.println("Thank You for Using Venn Create! (Exception)");
			}
		}
		e.consume();
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
	
	@FXML
	private void addCircle() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));
		
		shapeSceneCont = mainApp.getShapeSceneController();
		shapeSceneCont.addCircle();
	}

}
