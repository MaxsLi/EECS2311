package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import views.MainApp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {

	@FXML
	private Button createNewBtn;

	@FXML
	private Button getExistingBtn;

	// Reference to the Main Application
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */

	public MenuSceneController() {
		// this.setMainApp(mainApp);

	}

	@FXML
	private void createNew() throws IOException {

		mainApp.switchScene("shapeScene", null); //null should be a file
	}

//	@FXML
//	private void loadLast() throws IOException {
//
//		mainApp.switchScene("load");
//
//	}
	
	
	@FXML
	private void getExisting(ActionEvent event) throws IOException {
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Resource File");
		 
		 
		 File currentDir = new File(System.getProperty("user.home") + File.separator + "VennCreateFiles" + File.separator);
		 
		 if( ! currentDir.exists()) {
			 currentDir = new File(System.getProperty("user.home"));
		 }
		 
	//	 int numOfCSVFiles = 0;
		 
//		 for(String f: currentDir.list()) {
//			 if(f.substring(f.length()-4, f.length()).equals(".csv")) {
//				 numOfCSVFiles++;
//			 }
//		 }
//		 
//		 if(numOfCSVFiles==0) {
//			 Alert alert = new Alert(AlertType.WARNING);
//				alert.setTitle("Warning Dialog");
//				alert.setHeaderText("Empty TextField");
//				alert.setContentText("Error Loading: Nothing to Load, Please Create a New Venn Diagram First");
//				alert.showAndWait(); 
//				return;
//		 }
		 
		 fileChooser.setInitialDirectory(currentDir);
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("CSV Files", "*.csv")
		 );
		 File selectedFile = fileChooser.showOpenDialog(null);
		 
		 
		 
		 
		 if(selectedFile == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("CSV Not Chosen");
				alert.setContentText("Please Chose Correct CSV and try again");
				alert.showAndWait();
		 }
		 else {
			 mainApp.switchScene("load", selectedFile);
		 }
		 
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

}
