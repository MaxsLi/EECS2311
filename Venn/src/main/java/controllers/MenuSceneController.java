package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import views.MainApp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {

	@FXML
	public ImageView titleImage;

	@FXML
	private Button createNewBttn;

	@FXML
	private Button getExistingBttn;

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

		mainApp.switchScene("shapeScene", "");
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
		 
		 File currentDir = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" 
		 + File.separator + "java" + File.separator + "resources" + File.separator);
		 
		 int numOfCSVFiles = 0;
		 
		 for(String f: currentDir.list()) {
			 if(f.substring(f.length()-4, f.length()).equals(".csv")) {
				 numOfCSVFiles++;
			 }
		 }
		 
		 if(numOfCSVFiles==0) {
			 Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("Empty TextField");
				alert.setContentText("Error Loading: Nothing to Load, Please Create a New Venn Diagram First");
				alert.showAndWait(); 
				return;
		 }
		 
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
			 String fileTitle = selectedFile.getName();
			 mainApp.switchScene("load", fileTitle);
		 }
		 
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			Image img = new Image(
					"file:" + System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
							+ File.separator + "java" + File.separator + "resources" + File.separator + "images" + File.separator
							+ "title.png");
			this.titleImage = new ImageView(img);
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
