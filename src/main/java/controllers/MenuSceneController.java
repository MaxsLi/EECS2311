package controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import views.MainApp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {

	@FXML
	private Button createNewBttn;

	@FXML
	private Button getExistingBttn;

	// Reference to the Main Application
	private MainApp mainApp;

	@FXML
	private Circle leftCircle;

	@FXML
	private Circle rightCircle;

	@FXML
	private Button testModeBttn;

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

		if (!currentDir.exists()) {
			currentDir = new File(System.getProperty("user.home"));
		}

		fileChooser.setInitialDirectory(currentDir);
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("CSV Files", "*.csv")
		);
		File selectedFile = fileChooser.showOpenDialog(null);


		if (selectedFile == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("CSV Not Chosen");
			alert.setContentText("Please Chose Correct CSV and try again");
			alert.showAndWait();
		} else {
			mainApp.switchScene("load", selectedFile);
		}

	}

	@FXML
	private void createGlow() {
		createNewBttn.setStyle("-fx-background-color:#c98b8b; -fx-border-width:5px;-fx-background-radius:50px;");
	}

	@FXML
	private void createNoGlow() {
		createNewBttn.setStyle("-fx-border-color:black;-fx-background-radius:50px;");
	}


	@FXML
	private void existingGlow() {
		getExistingBttn.setStyle("-fx-background-color:#00ffc3; -fx-border-width:5px;-fx-background-radius:50px;");
	}

	@FXML
	private void existingNoGlow() {
		getExistingBttn.setStyle("-fx-border-color:black;-fx-background-radius:50px;");
	}

	@FXML
	private void testModeGlow() {
		testModeBttn.setStyle("-fx-background-color:#cc16dd; -fx-border-width:5px;-fx-background-radius:50px;");
	}

	@FXML
	private void testModeNoGlow() {
		testModeBttn.setStyle("-fx-border-color:black;-fx-background-radius:50px;");
	}

	@FXML
	private void goTestMode() throws IOException {
		mainApp.switchScene("testMode", null); //null should be a file
	}


	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TranslateTransition translateLeft = new TranslateTransition();
		TranslateTransition translateRight = new TranslateTransition();


		FadeTransition ftLeft = new FadeTransition(Duration.millis(3000), this.leftCircle);
		ftLeft.setFromValue(0);
		ftLeft.setToValue(1);
		ftLeft.setAutoReverse(true);

		FadeTransition ftRight = new FadeTransition(Duration.millis(3000), this.rightCircle);
		ftRight.setFromValue(0);
		ftRight.setToValue(1);
		ftRight.setAutoReverse(true);

		translateLeft.setByX(200);
		translateRight.setByX(-200);

		translateLeft.setDuration(Duration.millis(2500));
		translateRight.setDuration(Duration.millis(2500));

		translateLeft.setAutoReverse(true);
		translateRight.setAutoReverse(true);

		translateLeft.setNode(this.leftCircle);
		translateRight.setNode(this.rightCircle);

		translateLeft.setCycleCount(Timeline.INDEFINITE);
		translateRight.setCycleCount(Timeline.INDEFINITE);


		translateLeft.play();
		translateRight.play();
		ftLeft.play();
		ftRight.play();

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
